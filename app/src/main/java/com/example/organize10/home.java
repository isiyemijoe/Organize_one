package com.example.organize10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class home extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    RecyclerView recyclerView;
    CardView cardView;
    ImageView sortIcon;
    private boolean isOpen = true;
    private ConstraintLayout layout_open, layout_close;
    private ImageView add_btn;
    private Button signup_btn;
    private TextView bluechipText, whiteCiptext, selectedMonth;
    ConstraintSet constraintSetOpen, getConstraintSetClose;
    ConstraintLayout calendarConstraint;
    TextView recycleDateopen;
    CompactCalendarView calendarView;
    // List<todo> mData;
    dateConverter dateConverter = new dateConverter();

    RecyclerViewAdapter adapter;
    DatePickerDialog datePicker;
    TimePickerDialog timePicker;
    EditText signup_taskname, signup_description, signup_day, signup_month, signup_year, signup_uptime;
    ImageView signup_date_picker, signup_alarm;
    Spinner priorityPicker;
    final Calendar cal = GregorianCalendar.getInstance();
    Calendar datepickerCalendar = GregorianCalendar.getInstance();
    Date pickerDialogDate = new Date();
    Date now = new Date();
    Dialog dialog;
    calendarTempStore calendarTempStore = new calendarTempStore();
    private viewModel mViewModel;
    TodoRepo repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // mData = new ArrayList<>();
        mViewModel =
                ViewModelProviders.of(this).get(viewModel.class);
        mViewModel.init();
        mViewModel.getTodo().observe(this, new Observer<List<todo>>() {
            @Override
            public void onChanged(List<todo> todos) {
                adapter.notifyDataSetChanged();
            }
        });
        repo = new TodoRepo();
        initializeViews();
        cal.setTime(now);
        constraintSetOpen = new ConstraintSet();  //origin
        getConstraintSetClose = new ConstraintSet(); //destination
        final dateConverter dateConverter = new dateConverter();
        bluechipText.setText("Monthly");
        whiteCiptext.setText("Daily");
        // Collections.sort(mData, comparePiriority);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        initializeAdapter();

        int mnt = cal.get(Calendar.MONTH);
        String sMnt = dateConverter.convertMonth(mnt);
        selectedMonth.setText(sMnt);

        String sSmnt = dateConverter.convertShortMonth(mnt);
        recycleDateopen.setText(sSmnt);
        getConstraintSetClose.clone(this, R.layout.daily_view);
        constraintSetOpen.clone(layout_open);

        recycleDateopen.setOnClickListener(v -> {
            if (isOpen) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(layout_open);
                }
                getConstraintSetClose.applyTo(layout_open);
                bluechipText.setText("Daily");
                whiteCiptext.setText("Monthly");
                isOpen = !isOpen;
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(layout_open);
                }
                bluechipText.setText("Monthly");
                whiteCiptext.setText("Daily");
                constraintSetOpen.applyTo(layout_open);
                isOpen = !isOpen;
            }

        });
        selectedMonth.setOnClickListener(v -> {
            recycleDateopen.setText(selectedMonth.getText());
            //  adapter.getFilter().filter(selectedMonth.getText());
        });

           /*recycleDateopen.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence s, int start, int count, int after) {
               }

               @Override
               public void onTextChanged(CharSequence s, int start, int before, int count) {

               }
               @Override
               public void afterTextChanged(Editable s) {

               }
           });*/

        sortIcon.setOnClickListener(v -> {
            Collections.sort(mViewModel.getTodo().getValue(), filter.comparePiriority);
            initializeAdapter();
            adapter.notifyDataSetChanged();
        });

        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {


            @Override
            public void onDayClick(Date dateClicked) {
                cal.setTime(dateClicked);
                Calendar pickedDate = GregorianCalendar.getInstance();
                pickedDate.setTime(dateClicked);

                int mnt = pickedDate.get(Calendar.MONTH);
                int day = pickedDate.get(Calendar.DAY_OF_MONTH);
                int year = pickedDate.get(Calendar.YEAR);
                String sMnt = dateConverter.convertMonth(mnt);
                recycleDateopen.setText(day + " " + sMnt);
                String filt = day + " " + dateConverter.convertMonth(mnt) + " " + year;
                adapter.notifyDataSetChanged();
                //adapter.getFilter().filter(filt);

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                cal.setTime(firstDayOfNewMonth);

                if (firstDayOfNewMonth.getYear() != now.getYear()) {
                    Calendar pickedDate = GregorianCalendar.getInstance();
                    pickedDate.setTime(firstDayOfNewMonth);
                    int year = pickedDate.get(Calendar.YEAR);
                    String newMont = dateConverter.convertMonth(firstDayOfNewMonth.getMonth());
                    selectedMonth.setText(newMont + " " + year);
                } else {
                    String newMont = dateConverter.convertMonth(firstDayOfNewMonth.getMonth());
                    selectedMonth.setText(newMont);
                }


            }
        });
        add_btn.setOnClickListener(v -> {
            dialog = new Dialog(home.this);
            dialog.setContentView(R.layout.sign_up_form);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            signup_btn = dialog.findViewById(R.id.signup_add_btn);
            signup_taskname = dialog.findViewById(R.id.signup_taskname);
            signup_description = dialog.findViewById(R.id.signup_description);
            signup_day = dialog.findViewById(R.id.signup_day);
            signup_month = dialog.findViewById(R.id.signup_month);
            signup_uptime = dialog.findViewById(R.id.signup_uptime);
            signup_year = dialog.findViewById(R.id.signup_year);
            signup_date_picker = dialog.findViewById(R.id.signup_date_picker);
            signup_alarm = dialog.findViewById(R.id.signup_alarm);
            priorityPicker = dialog.findViewById(R.id.signup_spinner);
            final String[] sPriority = new String[1];


            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.spinner_item, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            priorityPicker.setAdapter(adapter);
            priorityPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    sPriority[0] = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    System.out.println("Spinner Error");
                    throw new NullPointerException();
                }
            });
            dialog.show();


            signup_date_picker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calnow = Calendar.getInstance();

                    datePicker = DatePickerDialog.newInstance(
                            home.this,
                            cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH),
                            cal.get(Calendar.DAY_OF_MONTH)
                    );
                    timePicker = TimePickerDialog.newInstance(
                            home.this,
                            cal.get(Calendar.HOUR_OF_DAY),
                            cal.get(Calendar.MINUTE),
                            cal.get(Calendar.SECOND),
                            false
                    );
                    datePicker.show(getSupportFragmentManager(), "Date Picker");

                    signup_btn.setOnClickListener(v1 -> {
                        Calendar cal = Calendar.getInstance();

                        int uptime = 0;
                        String name = null;
                        String desc = null;
                        if (signup_taskname.getText().toString().isEmpty() || signup_description.getText().toString().isEmpty()) {
                            System.out.println("invalid Name and description");
                        } else {
                            name = signup_taskname.getText().toString();
                            desc = signup_description.getText().toString();
                        }
                        if (calendarTempStore.getDate() != null) {
                            cal = calendarTempStore.getDate();
                        }

                        if (signup_year.getText() != null && signup_day.getText() != null && signup_description.getText() != null) {
                            int day;
                            int month;
                            int year;
                            try {
                                day = Integer.parseInt(signup_day.getText().toString());
                                month = Integer.parseInt(signup_month.getText().toString());
                                year = Integer.parseInt(signup_year.getText().toString());
                                int hour = now.getHours();
                                int min = now.getMinutes();
                                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                                String df = day + "/" + month + "/" + year + " " + hour + ":" + min;
                                try {
                                    Date date = format.parse(df);
                                    cal.setTime(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }

                        }


                        if (!signup_uptime.getText().toString().isEmpty()) {
                            try {
                                uptime = Integer.parseInt(signup_uptime.getText().toString());
                                cal.add(Calendar.MINUTE, -uptime);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid option");
                                e.printStackTrace();
                            }

                        } else {
                            uptime = 0;
                        }
                        addTodo(name, desc, cal, uptime, sPriority[0]);
                        Calendar notifyCal = cal;
                        signup_alarm.setOnClickListener(new View.OnClickListener() {
                            Date mmdatee = notifyCal.getTime();

                            @Override
                            public void onClick(View v) {
                                Notify notify = new Notify(signup_taskname.getText().toString(), getApplicationContext(), mmdatee);
                            }

                        });
                        dialog.dismiss();
                    });

                }

            });

        });

    }

    private void initializeAdapter() {
        adapter = new RecyclerViewAdapter(mViewModel.getTodo().getValue(), this.getApplicationContext());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initializeViews() {

        layout_open = findViewById(R.id.constraintLayout_open);
        recycleDateopen = findViewById(R.id.recyclerview_date_open);
        calendarConstraint = findViewById(R.id.calendarView);
        recycleDateopen = findViewById(R.id.recyclerview_date_open);
        calendarView = findViewById(R.id.compactCalendar);
        bluechipText = findViewById(R.id.bluechip_text);
        whiteCiptext = findViewById(R.id.whiteChip_text);
        selectedMonth = findViewById(R.id.month);
        sortIcon = findViewById(R.id.sort_icon);
        add_btn = findViewById(R.id.btn_add);
    }

    public void addTodo(String name, String description, Calendar date, int uptime, String
            spriority) {

        dateConverter converter = new dateConverter();
        double int_priority = priority.initializePriority(spriority);
        int color = priority.priorityColor(int_priority);
        boolean isCompleted = false;
        Calendar cal2 = date;
        Date dates = cal2.getTime();
        String year = String.valueOf(cal2.get(Calendar.YEAR));
        String month = converter.convertMonth(cal2.get(Calendar.MONTH));
        String day = String.valueOf(cal2.get(Calendar.DAY_OF_MONTH));
        String key = day + " " + month + " " + year;
        Event event = new Event(color, dates.getTime());
        String monthKey = month + " " + year;
        todo todo = new todo(name, description, dates, uptime, color, int_priority, isCompleted, key, monthKey, event);
        repo.addTodo(todo);
        initializeAdapter();
        adapter.notifyDataSetChanged();
        calendarView.addEvent(event, false);
        Toast.makeText(home.this, todo.getName() + " has been added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        datepickerCalendar.set(Calendar.YEAR, year);
        datepickerCalendar.set(Calendar.MONTH, monthOfYear);
        datepickerCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        timePicker.show(getSupportFragmentManager(), "DatePickerDialog");
        signup_day.setText(String.valueOf(dayOfMonth + 1));
        signup_month.setText(String.valueOf(dateConverter.convertShortMonth(monthOfYear)));
        signup_year.setText(String.valueOf(year));
        String key = dayOfMonth + " " + monthOfYear + " " + year;
        adapter.getFilter().filter(key);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        datepickerCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        datepickerCalendar.set(Calendar.MINUTE, minute);
        datepickerCalendar.set(Calendar.SECOND, second);
        calendarTempStore.setDate(datepickerCalendar);
        System.out.println(calendarTempStore.getDate().toString());

    }


}