package in.zipgo.aabhasjindal.googletimepicker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TimePickerActivity extends AppCompatActivity {

    TextView output;
    RecyclerView snappyRecyclerView;
    LinearLayoutManager linearLayoutManager;
    TimePickerAdapter timePickerAdapter;
    List<TimePickerElement> timePickerElementList;
    ClickListener clickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_time_picker);
        output = findViewById(R.id.output);
        snappyRecyclerView = findViewById(R.id.home_recyclerView);
        snappyRecyclerView.setNestedScrollingEnabled(false);
        timePickerElementList = TimePickerElement.getDefaultTimeList();
        clickListener = position -> linearLayoutManager.scrollToPositionWithOffset(position - 2, 0);
        timePickerAdapter = new TimePickerAdapter(timePickerElementList, clickListener);
        linearLayoutManager = new SliderLayoutManager(this, LinearLayoutManager.HORIZONTAL, false, new SliderLayoutManager.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int layoutPosition) {
                output.setText(TimePickerElement.getDefaultTimeList().get(layoutPosition).time);
                timePickerAdapter.highlightItem(layoutPosition);
                timePickerAdapter.setClickListener(clickListener);
                snappyRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        
                    }
                });
            }

            @Override
            public void onScrolled() {
                timePickerAdapter.resetSelection();
                timePickerAdapter.notifyDataSetChanged();
                timePickerAdapter.setClickListener(null);
            }
        });

//        SnapHelper snapHelper = new GravitySnapHelper(Gravity.CENTER_HORIZONTAL);
//        SnapHelper snapHelper = new CenterSnapHelper();
//        snapHelper.attachToRecyclerView(snappyRecyclerView);

        snappyRecyclerView.setScrollingTouchSlop(RecyclerView.TOUCH_SLOP_PAGING);
        snappyRecyclerView.setLayoutManager(linearLayoutManager);
        snappyRecyclerView.setAdapter(timePickerAdapter);

    }


    //todo adapters

    private class TimePickerAdapter extends RecyclerView.Adapter<TimePickerAdapter.TimePickerViewHolder> {
        private List<TimePickerElement> data;
        private ClickListener clickListener;

        TimePickerAdapter(List<TimePickerElement> data, ClickListener clickListener) {
            this.data = data;
            this.clickListener = clickListener;
        }

        public void setClickListener(ClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @NonNull
        @Override
        public TimePickerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new TimePickerViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_time_picker, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull TimePickerViewHolder timePickerViewHolder, int position) {
            timePickerViewHolder.onBind(data.get(position), position);
        }

        @Override
        public int getItemCount() {
            return (data != null) ? data.size() : 0;
        }

        class TimePickerViewHolder extends RecyclerView.ViewHolder {
            TimePickerViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            void onBind(TimePickerElement element, int position) {
                LinearLayout linearLayout = itemView.findViewById(R.id.item_time_ll);
                TextView time = itemView.findViewById(R.id.item_time);
                TextView format = itemView.findViewById(R.id.item_format);
                time.setText(element.time);
                format.setText(element.format);
                if (element.selected) {
                    time.setEnabled(false);
                    format.setEnabled(false);
                    time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    format.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                } else {
                    time.setEnabled(true);
                    format.setEnabled(true);
                    time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    format.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                }
                if (!TextUtils.isEmpty(element.time) && !TextUtils.isEmpty(element.format)) {
                    linearLayout.setOnClickListener((v) -> {
                        highlightItem(position);
                        if (clickListener != null) {
                            clickListener.onClick(position);
                        }
                    });
                }

            }
        }

        private void resetSelection() {
            for (TimePickerElement element : data) {
                element.selected = false;
            }
        }

        void highlightItem(int position) {
            resetSelection();
            data.get(position).selected = true;
            notifyDataSetChanged();
        }
    }

    static class TimePickerElement {
        private static final String AM = "AM";
        private static final String PM = "PM";
        private String time;
        private String format;
        private boolean selected;

        TimePickerElement(String time, String format) {
            this.time = time;
            this.format = format;
            this.selected = false;
        }

        static List<TimePickerElement> getDefaultTimeList() {
            List<TimePickerElement> timePickerElementList = new ArrayList<>();
            timePickerElementList.add(new TimePickerElement("", ""));
            timePickerElementList.add(new TimePickerElement("", ""));
            timePickerElementList.add(new TimePickerElement("12:00", AM));
            timePickerElementList.add(new TimePickerElement("12:15", AM));
            timePickerElementList.add(new TimePickerElement("12:30", AM));
            timePickerElementList.add(new TimePickerElement("12:45", AM));
            timePickerElementList.add(new TimePickerElement("1:00", AM));
            timePickerElementList.add(new TimePickerElement("1:15", AM));
            timePickerElementList.add(new TimePickerElement("1:30", AM));
            timePickerElementList.add(new TimePickerElement("1:45", AM));
            timePickerElementList.add(new TimePickerElement("2:00", AM));
            timePickerElementList.add(new TimePickerElement("2:15", AM));
            timePickerElementList.add(new TimePickerElement("2:30", AM));
            timePickerElementList.add(new TimePickerElement("2:45", AM));
            timePickerElementList.add(new TimePickerElement("3:00", AM));
            timePickerElementList.add(new TimePickerElement("3:15", AM));
            timePickerElementList.add(new TimePickerElement("3:30", AM));
            timePickerElementList.add(new TimePickerElement("3:45", AM));
            timePickerElementList.add(new TimePickerElement("4:00", AM));
            timePickerElementList.add(new TimePickerElement("4:15", AM));
            timePickerElementList.add(new TimePickerElement("4:30", AM));
            timePickerElementList.add(new TimePickerElement("4:45", AM));
            timePickerElementList.add(new TimePickerElement("5:00", AM));
            timePickerElementList.add(new TimePickerElement("5:15", AM));
            timePickerElementList.add(new TimePickerElement("5:30", AM));
            timePickerElementList.add(new TimePickerElement("5:45", AM));
            timePickerElementList.add(new TimePickerElement("6:00", AM));
            timePickerElementList.add(new TimePickerElement("6:15", AM));
            timePickerElementList.add(new TimePickerElement("6:30", AM));
            timePickerElementList.add(new TimePickerElement("6:45", AM));
            timePickerElementList.add(new TimePickerElement("7:00", AM));
            timePickerElementList.add(new TimePickerElement("7:15", AM));
            timePickerElementList.add(new TimePickerElement("7:30", AM));
            timePickerElementList.add(new TimePickerElement("7:45", AM));
            timePickerElementList.add(new TimePickerElement("8:00", AM));
            timePickerElementList.add(new TimePickerElement("8:15", AM));
            timePickerElementList.add(new TimePickerElement("8:30", AM));
            timePickerElementList.add(new TimePickerElement("8:45", AM));
            timePickerElementList.add(new TimePickerElement("9:00", AM));
            timePickerElementList.add(new TimePickerElement("9:15", AM));
            timePickerElementList.add(new TimePickerElement("9:30", AM));
            timePickerElementList.add(new TimePickerElement("9:45", AM));
            timePickerElementList.add(new TimePickerElement("10:00", AM));
            timePickerElementList.add(new TimePickerElement("10:15", AM));
            timePickerElementList.add(new TimePickerElement("10:30", AM));
            timePickerElementList.add(new TimePickerElement("10:45", AM));
            timePickerElementList.add(new TimePickerElement("11:00", AM));
            timePickerElementList.add(new TimePickerElement("11:15", AM));
            timePickerElementList.add(new TimePickerElement("11:30", AM));
            timePickerElementList.add(new TimePickerElement("11:45", AM));
            timePickerElementList.add(new TimePickerElement("12:00", PM));
            timePickerElementList.add(new TimePickerElement("12:15", PM));
            timePickerElementList.add(new TimePickerElement("12:30", PM));
            timePickerElementList.add(new TimePickerElement("12:45", PM));
            timePickerElementList.add(new TimePickerElement("1:00", PM));
            timePickerElementList.add(new TimePickerElement("1:15", PM));
            timePickerElementList.add(new TimePickerElement("1:30", PM));
            timePickerElementList.add(new TimePickerElement("1:45", PM));
            timePickerElementList.add(new TimePickerElement("2:00", PM));
            timePickerElementList.add(new TimePickerElement("2:15", PM));
            timePickerElementList.add(new TimePickerElement("2:30", PM));
            timePickerElementList.add(new TimePickerElement("2:45", PM));
            timePickerElementList.add(new TimePickerElement("3:00", PM));
            timePickerElementList.add(new TimePickerElement("3:15", PM));
            timePickerElementList.add(new TimePickerElement("3:30", PM));
            timePickerElementList.add(new TimePickerElement("3:45", PM));
            timePickerElementList.add(new TimePickerElement("4:00", PM));
            timePickerElementList.add(new TimePickerElement("4:15", PM));
            timePickerElementList.add(new TimePickerElement("4:30", PM));
            timePickerElementList.add(new TimePickerElement("4:45", PM));
            timePickerElementList.add(new TimePickerElement("5:00", PM));
            timePickerElementList.add(new TimePickerElement("5:15", PM));
            timePickerElementList.add(new TimePickerElement("5:30", PM));
            timePickerElementList.add(new TimePickerElement("5:45", PM));
            timePickerElementList.add(new TimePickerElement("6:00", PM));
            timePickerElementList.add(new TimePickerElement("6:15", PM));
            timePickerElementList.add(new TimePickerElement("6:30", PM));
            timePickerElementList.add(new TimePickerElement("6:45", PM));
            timePickerElementList.add(new TimePickerElement("7:00", PM));
            timePickerElementList.add(new TimePickerElement("7:15", PM));
            timePickerElementList.add(new TimePickerElement("7:30", PM));
            timePickerElementList.add(new TimePickerElement("7:45", PM));
            timePickerElementList.add(new TimePickerElement("8:00", PM));
            timePickerElementList.add(new TimePickerElement("8:15", PM));
            timePickerElementList.add(new TimePickerElement("8:30", PM));
            timePickerElementList.add(new TimePickerElement("8:45", PM));
            timePickerElementList.add(new TimePickerElement("9:00", PM));
            timePickerElementList.add(new TimePickerElement("9:15", PM));
            timePickerElementList.add(new TimePickerElement("9:30", PM));
            timePickerElementList.add(new TimePickerElement("9:45", PM));
            timePickerElementList.add(new TimePickerElement("10:00", PM));
            timePickerElementList.add(new TimePickerElement("10:15", PM));
            timePickerElementList.add(new TimePickerElement("10:30", PM));
            timePickerElementList.add(new TimePickerElement("10:45", PM));
            timePickerElementList.add(new TimePickerElement("11:00", PM));
            timePickerElementList.add(new TimePickerElement("11:15", PM));
            timePickerElementList.add(new TimePickerElement("11:30", PM));
            timePickerElementList.add(new TimePickerElement("11:45", PM));
            timePickerElementList.add(new TimePickerElement("", ""));
            timePickerElementList.add(new TimePickerElement("", ""));

            return timePickerElementList;
        }
    }

    public interface ClickListener {
        void onClick(int position);
    }
}
