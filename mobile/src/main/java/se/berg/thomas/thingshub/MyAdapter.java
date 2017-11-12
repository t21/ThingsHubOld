package se.berg.thomas.thingshub;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import se.berg.thomas.commonfunclib.WalnutDevice;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by thomas on 2017-08-27.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.WalnutViewHolder> {

    private static final String TAG = "MyAdapter";

    public interface OnItemClickListener {
        void onItemClick(WalnutDevice item);
    }
    private ArrayList<WalnutDevice> mWalnutArrayList;
    private final OnItemClickListener mOnClickListener;

    MyAdapter(ArrayList<WalnutDevice> walnutArrayList, OnItemClickListener listener){
        mWalnutArrayList = walnutArrayList;
        mOnClickListener = listener;
    }

    @Override
    public WalnutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.walnut_item2, parent, false);
        WalnutViewHolder vh = new WalnutViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(WalnutViewHolder viewHolder, int position) {
//        Log.v(TAG, mWalnutArrayList.get(position).getAddress());
//        Log.v(TAG, String.valueOf(position));
//        Log.v(TAG, String.valueOf(mWalnutArrayList.get(position).getSensorListNbrOfItems()));
        viewHolder.bind(mWalnutArrayList.get(position), mOnClickListener);

        viewHolder.walnutName.setText(mWalnutArrayList.get(position).getName());
        viewHolder.walnutDeviceAddress.setText(mWalnutArrayList.get(position).getAddress());
        viewHolder.walnutRssi.setText(mWalnutArrayList.get(position).getRssi().toString() + "dBm");
        viewHolder.walnutBattery.setText(mWalnutArrayList.get(position).getBatteryCapacity().toString() + "%");

        switch (mWalnutArrayList.get(position).getSensorListNbrOfItems()) {
            case 0:
                viewHolder.sensorName1.setVisibility(GONE);
                viewHolder.sensorValue1.setVisibility(GONE);
                viewHolder.sensorName2.setVisibility(GONE);
                viewHolder.sensorValue2.setVisibility(GONE);
                viewHolder.sensorName3.setVisibility(GONE);
                viewHolder.sensorValue3.setVisibility(GONE);
                viewHolder.sensorName4.setVisibility(GONE);
                viewHolder.sensorValue4.setVisibility(GONE);
                viewHolder.sensorName5.setVisibility(GONE);
                viewHolder.sensorValue5.setVisibility(GONE);
                viewHolder.sensorName6.setVisibility(GONE);
                viewHolder.sensorValue6.setVisibility(GONE);
                viewHolder.sensorName7.setVisibility(GONE);
                viewHolder.sensorValue7.setVisibility(GONE);
                viewHolder.sensorName8.setVisibility(GONE);
                viewHolder.sensorValue8.setVisibility(GONE);
                break;
            case 1:
                viewHolder.sensorName1.setVisibility(VISIBLE);
                viewHolder.sensorValue1.setVisibility(VISIBLE);
                viewHolder.sensorName2.setVisibility(GONE);
                viewHolder.sensorValue2.setVisibility(GONE);
                viewHolder.sensorName3.setVisibility(GONE);
                viewHolder.sensorValue3.setVisibility(GONE);
                viewHolder.sensorName4.setVisibility(GONE);
                viewHolder.sensorValue4.setVisibility(GONE);
                viewHolder.sensorName5.setVisibility(GONE);
                viewHolder.sensorValue5.setVisibility(GONE);
                viewHolder.sensorName6.setVisibility(GONE);
                viewHolder.sensorValue6.setVisibility(GONE);
                viewHolder.sensorName7.setVisibility(GONE);
                viewHolder.sensorValue7.setVisibility(GONE);
                viewHolder.sensorName8.setVisibility(GONE);
                viewHolder.sensorValue8.setVisibility(GONE);
                viewHolder.sensorName1.setText(mWalnutArrayList.get(position).getSensorName(0));
                viewHolder.sensorValue1.setText(mWalnutArrayList.get(position).getSensorValue(0));
                break;
            case 2:
                viewHolder.sensorName1.setVisibility(VISIBLE);
                viewHolder.sensorValue1.setVisibility(VISIBLE);
                viewHolder.sensorName2.setVisibility(VISIBLE);
                viewHolder.sensorValue2.setVisibility(VISIBLE);
                viewHolder.sensorName3.setVisibility(GONE);
                viewHolder.sensorValue3.setVisibility(GONE);
                viewHolder.sensorName4.setVisibility(GONE);
                viewHolder.sensorValue4.setVisibility(GONE);
                viewHolder.sensorName5.setVisibility(GONE);
                viewHolder.sensorValue5.setVisibility(GONE);
                viewHolder.sensorName6.setVisibility(GONE);
                viewHolder.sensorValue6.setVisibility(GONE);
                viewHolder.sensorName7.setVisibility(GONE);
                viewHolder.sensorValue7.setVisibility(GONE);
                viewHolder.sensorName8.setVisibility(GONE);
                viewHolder.sensorValue8.setVisibility(GONE);
                viewHolder.sensorName1.setText(mWalnutArrayList.get(position).getSensorName(0));
                viewHolder.sensorValue1.setText(mWalnutArrayList.get(position).getSensorValue(0));
                viewHolder.sensorName2.setText(mWalnutArrayList.get(position).getSensorName(1));
                viewHolder.sensorValue2.setText(mWalnutArrayList.get(position).getSensorValue(1));
                break;
            case 3:
                viewHolder.sensorName1.setVisibility(VISIBLE);
                viewHolder.sensorValue1.setVisibility(VISIBLE);
                viewHolder.sensorName2.setVisibility(VISIBLE);
                viewHolder.sensorValue2.setVisibility(VISIBLE);
                viewHolder.sensorName3.setVisibility(VISIBLE);
                viewHolder.sensorValue3.setVisibility(VISIBLE);
                viewHolder.sensorName4.setVisibility(GONE);
                viewHolder.sensorValue4.setVisibility(GONE);
                viewHolder.sensorName5.setVisibility(GONE);
                viewHolder.sensorValue5.setVisibility(GONE);
                viewHolder.sensorName6.setVisibility(GONE);
                viewHolder.sensorValue6.setVisibility(GONE);
                viewHolder.sensorName7.setVisibility(GONE);
                viewHolder.sensorValue7.setVisibility(GONE);
                viewHolder.sensorName8.setVisibility(GONE);
                viewHolder.sensorValue8.setVisibility(GONE);
                viewHolder.sensorName1.setText(mWalnutArrayList.get(position).getSensorName(0));
                viewHolder.sensorValue1.setText(mWalnutArrayList.get(position).getSensorValue(0));
                viewHolder.sensorName2.setText(mWalnutArrayList.get(position).getSensorName(1));
                viewHolder.sensorValue2.setText(mWalnutArrayList.get(position).getSensorValue(1));
                viewHolder.sensorName3.setText(mWalnutArrayList.get(position).getSensorName(2));
                viewHolder.sensorValue3.setText(mWalnutArrayList.get(position).getSensorValue(2));
                break;
            case 4:
                viewHolder.sensorName1.setVisibility(VISIBLE);
                viewHolder.sensorValue1.setVisibility(VISIBLE);
                viewHolder.sensorName2.setVisibility(VISIBLE);
                viewHolder.sensorValue2.setVisibility(VISIBLE);
                viewHolder.sensorName3.setVisibility(VISIBLE);
                viewHolder.sensorValue3.setVisibility(VISIBLE);
                viewHolder.sensorName4.setVisibility(VISIBLE);
                viewHolder.sensorValue4.setVisibility(VISIBLE);
                viewHolder.sensorName5.setVisibility(GONE);
                viewHolder.sensorValue5.setVisibility(GONE);
                viewHolder.sensorName6.setVisibility(GONE);
                viewHolder.sensorValue6.setVisibility(GONE);
                viewHolder.sensorName7.setVisibility(GONE);
                viewHolder.sensorValue7.setVisibility(GONE);
                viewHolder.sensorName8.setVisibility(GONE);
                viewHolder.sensorValue8.setVisibility(GONE);
                viewHolder.sensorName1.setText(mWalnutArrayList.get(position).getSensorName(0));
                viewHolder.sensorValue1.setText(mWalnutArrayList.get(position).getSensorValue(0));
                viewHolder.sensorName2.setText(mWalnutArrayList.get(position).getSensorName(1));
                viewHolder.sensorValue2.setText(mWalnutArrayList.get(position).getSensorValue(1));
                viewHolder.sensorName3.setText(mWalnutArrayList.get(position).getSensorName(2));
                viewHolder.sensorValue3.setText(mWalnutArrayList.get(position).getSensorValue(2));
                viewHolder.sensorName4.setText(mWalnutArrayList.get(position).getSensorName(3));
                viewHolder.sensorValue4.setText(mWalnutArrayList.get(position).getSensorValue(3));
                break;
            case 5:
                viewHolder.sensorName1.setVisibility(VISIBLE);
                viewHolder.sensorValue1.setVisibility(VISIBLE);
                viewHolder.sensorName2.setVisibility(VISIBLE);
                viewHolder.sensorValue2.setVisibility(VISIBLE);
                viewHolder.sensorName3.setVisibility(VISIBLE);
                viewHolder.sensorValue3.setVisibility(VISIBLE);
                viewHolder.sensorName4.setVisibility(VISIBLE);
                viewHolder.sensorValue4.setVisibility(VISIBLE);
                viewHolder.sensorName5.setVisibility(VISIBLE);
                viewHolder.sensorValue5.setVisibility(VISIBLE);
                viewHolder.sensorName6.setVisibility(GONE);
                viewHolder.sensorValue6.setVisibility(GONE);
                viewHolder.sensorName7.setVisibility(GONE);
                viewHolder.sensorValue7.setVisibility(GONE);
                viewHolder.sensorName8.setVisibility(GONE);
                viewHolder.sensorValue8.setVisibility(GONE);
                viewHolder.sensorName1.setText(mWalnutArrayList.get(position).getSensorName(0));
                viewHolder.sensorValue1.setText(mWalnutArrayList.get(position).getSensorValue(0));
                viewHolder.sensorName2.setText(mWalnutArrayList.get(position).getSensorName(1));
                viewHolder.sensorValue2.setText(mWalnutArrayList.get(position).getSensorValue(1));
                viewHolder.sensorName3.setText(mWalnutArrayList.get(position).getSensorName(2));
                viewHolder.sensorValue3.setText(mWalnutArrayList.get(position).getSensorValue(2));
                viewHolder.sensorName4.setText(mWalnutArrayList.get(position).getSensorName(3));
                viewHolder.sensorValue4.setText(mWalnutArrayList.get(position).getSensorValue(3));
                viewHolder.sensorName5.setText(mWalnutArrayList.get(position).getSensorName(4));
                viewHolder.sensorValue5.setText(mWalnutArrayList.get(position).getSensorValue(4));
                break;
            case 6:
                viewHolder.sensorName1.setVisibility(VISIBLE);
                viewHolder.sensorValue1.setVisibility(VISIBLE);
                viewHolder.sensorName2.setVisibility(VISIBLE);
                viewHolder.sensorValue2.setVisibility(VISIBLE);
                viewHolder.sensorName3.setVisibility(VISIBLE);
                viewHolder.sensorValue3.setVisibility(VISIBLE);
                viewHolder.sensorName4.setVisibility(VISIBLE);
                viewHolder.sensorValue4.setVisibility(VISIBLE);
                viewHolder.sensorName5.setVisibility(VISIBLE);
                viewHolder.sensorValue5.setVisibility(VISIBLE);
                viewHolder.sensorName6.setVisibility(VISIBLE);
                viewHolder.sensorValue6.setVisibility(VISIBLE);
                viewHolder.sensorName7.setVisibility(GONE);
                viewHolder.sensorValue7.setVisibility(GONE);
                viewHolder.sensorName8.setVisibility(GONE);
                viewHolder.sensorValue8.setVisibility(GONE);
                viewHolder.sensorName1.setText(mWalnutArrayList.get(position).getSensorName(0));
                viewHolder.sensorValue1.setText(mWalnutArrayList.get(position).getSensorValue(0));
                viewHolder.sensorName2.setText(mWalnutArrayList.get(position).getSensorName(1));
                viewHolder.sensorValue2.setText(mWalnutArrayList.get(position).getSensorValue(1));
                viewHolder.sensorName3.setText(mWalnutArrayList.get(position).getSensorName(2));
                viewHolder.sensorValue3.setText(mWalnutArrayList.get(position).getSensorValue(2));
                viewHolder.sensorName4.setText(mWalnutArrayList.get(position).getSensorName(3));
                viewHolder.sensorValue4.setText(mWalnutArrayList.get(position).getSensorValue(3));
                viewHolder.sensorName5.setText(mWalnutArrayList.get(position).getSensorName(4));
                viewHolder.sensorValue5.setText(mWalnutArrayList.get(position).getSensorValue(4));
                viewHolder.sensorName6.setText(mWalnutArrayList.get(position).getSensorName(5));
                viewHolder.sensorValue6.setText(mWalnutArrayList.get(position).getSensorValue(5));
                break;
            case 7:
                viewHolder.sensorName1.setVisibility(VISIBLE);
                viewHolder.sensorValue1.setVisibility(VISIBLE);
                viewHolder.sensorName2.setVisibility(VISIBLE);
                viewHolder.sensorValue2.setVisibility(VISIBLE);
                viewHolder.sensorName3.setVisibility(VISIBLE);
                viewHolder.sensorValue3.setVisibility(VISIBLE);
                viewHolder.sensorName4.setVisibility(VISIBLE);
                viewHolder.sensorValue4.setVisibility(VISIBLE);
                viewHolder.sensorName5.setVisibility(VISIBLE);
                viewHolder.sensorValue5.setVisibility(VISIBLE);
                viewHolder.sensorName6.setVisibility(VISIBLE);
                viewHolder.sensorValue6.setVisibility(VISIBLE);
                viewHolder.sensorName7.setVisibility(VISIBLE);
                viewHolder.sensorValue7.setVisibility(VISIBLE);
                viewHolder.sensorName8.setVisibility(GONE);
                viewHolder.sensorValue8.setVisibility(GONE);
                viewHolder.sensorName1.setText(mWalnutArrayList.get(position).getSensorName(0));
                viewHolder.sensorValue1.setText(mWalnutArrayList.get(position).getSensorValue(0));
                viewHolder.sensorName2.setText(mWalnutArrayList.get(position).getSensorName(1));
                viewHolder.sensorValue2.setText(mWalnutArrayList.get(position).getSensorValue(1));
                viewHolder.sensorName3.setText(mWalnutArrayList.get(position).getSensorName(2));
                viewHolder.sensorValue3.setText(mWalnutArrayList.get(position).getSensorValue(2));
                viewHolder.sensorName4.setText(mWalnutArrayList.get(position).getSensorName(3));
                viewHolder.sensorValue4.setText(mWalnutArrayList.get(position).getSensorValue(3));
                viewHolder.sensorName5.setText(mWalnutArrayList.get(position).getSensorName(4));
                viewHolder.sensorValue5.setText(mWalnutArrayList.get(position).getSensorValue(4));
                viewHolder.sensorName6.setText(mWalnutArrayList.get(position).getSensorName(5));
                viewHolder.sensorValue6.setText(mWalnutArrayList.get(position).getSensorValue(5));
                viewHolder.sensorName7.setText(mWalnutArrayList.get(position).getSensorName(6));
                viewHolder.sensorValue7.setText(mWalnutArrayList.get(position).getSensorValue(6));
                break;
            case 8:
                viewHolder.sensorName1.setVisibility(VISIBLE);
                viewHolder.sensorValue1.setVisibility(VISIBLE);
                viewHolder.sensorName2.setVisibility(VISIBLE);
                viewHolder.sensorValue2.setVisibility(VISIBLE);
                viewHolder.sensorName3.setVisibility(VISIBLE);
                viewHolder.sensorValue3.setVisibility(VISIBLE);
                viewHolder.sensorName4.setVisibility(VISIBLE);
                viewHolder.sensorValue4.setVisibility(VISIBLE);
                viewHolder.sensorName5.setVisibility(VISIBLE);
                viewHolder.sensorValue5.setVisibility(VISIBLE);
                viewHolder.sensorName6.setVisibility(VISIBLE);
                viewHolder.sensorValue6.setVisibility(VISIBLE);
                viewHolder.sensorName7.setVisibility(VISIBLE);
                viewHolder.sensorValue7.setVisibility(VISIBLE);
                viewHolder.sensorName8.setVisibility(VISIBLE);
                viewHolder.sensorValue8.setVisibility(VISIBLE);
                viewHolder.sensorName1.setText(mWalnutArrayList.get(position).getSensorName(0));
                viewHolder.sensorValue1.setText(mWalnutArrayList.get(position).getSensorValue(0));
                viewHolder.sensorName2.setText(mWalnutArrayList.get(position).getSensorName(1));
                viewHolder.sensorValue2.setText(mWalnutArrayList.get(position).getSensorValue(1));
                viewHolder.sensorName3.setText(mWalnutArrayList.get(position).getSensorName(2));
                viewHolder.sensorValue3.setText(mWalnutArrayList.get(position).getSensorValue(2));
                viewHolder.sensorName4.setText(mWalnutArrayList.get(position).getSensorName(3));
                viewHolder.sensorValue4.setText(mWalnutArrayList.get(position).getSensorValue(3));
                viewHolder.sensorName5.setText(mWalnutArrayList.get(position).getSensorName(4));
                viewHolder.sensorValue5.setText(mWalnutArrayList.get(position).getSensorValue(4));
                viewHolder.sensorName6.setText(mWalnutArrayList.get(position).getSensorName(5));
                viewHolder.sensorValue6.setText(mWalnutArrayList.get(position).getSensorValue(5));
                viewHolder.sensorName7.setText(mWalnutArrayList.get(position).getSensorName(6));
                viewHolder.sensorValue7.setText(mWalnutArrayList.get(position).getSensorValue(6));
                viewHolder.sensorName8.setText(mWalnutArrayList.get(position).getSensorName(7));
                viewHolder.sensorValue8.setText(mWalnutArrayList.get(position).getSensorValue(7));
                break;
            default:
                viewHolder.sensorName1.setVisibility(GONE);
                viewHolder.sensorValue1.setVisibility(GONE);
                viewHolder.sensorName2.setVisibility(GONE);
                viewHolder.sensorValue2.setVisibility(GONE);
                viewHolder.sensorName3.setVisibility(GONE);
                viewHolder.sensorValue3.setVisibility(GONE);
                viewHolder.sensorName4.setVisibility(GONE);
                viewHolder.sensorValue4.setVisibility(GONE);
                viewHolder.sensorName5.setVisibility(GONE);
                viewHolder.sensorValue5.setVisibility(GONE);
                viewHolder.sensorName6.setVisibility(GONE);
                viewHolder.sensorValue6.setVisibility(GONE);
                viewHolder.sensorName7.setVisibility(GONE);
                viewHolder.sensorValue7.setVisibility(GONE);
                viewHolder.sensorName8.setVisibility(GONE);
                viewHolder.sensorValue8.setVisibility(GONE);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mWalnutArrayList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class WalnutViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView walnutName;
        TextView walnutDeviceAddress;
        TextView walnutRssi;
        TextView walnutBattery;
        TextView sensorName1, sensorName2, sensorName3, sensorName4, sensorName5;
        TextView sensorName6, sensorName7, sensorName8;
        TextView sensorValue1, sensorValue2, sensorValue3, sensorValue4, sensorValue5;
        TextView sensorValue6, sensorValue7, sensorValue8;

        WalnutViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            walnutName = itemView.findViewById(R.id.walnut_name);
            walnutDeviceAddress = itemView.findViewById(R.id.walnut_device_address);
            walnutRssi = itemView.findViewById(R.id.walnut_rssi);
            walnutBattery = itemView.findViewById(R.id.walnut_battery);
            sensorName1 = itemView.findViewById(R.id.sensor_1_name);
            sensorValue1 = itemView.findViewById(R.id.sensor_1_value);
            sensorName2 = itemView.findViewById(R.id.sensor_2_name);
            sensorValue2 = itemView.findViewById(R.id.sensor_2_value);
            sensorName3 = itemView.findViewById(R.id.sensor_3_name);
            sensorValue3 = itemView.findViewById(R.id.sensor_3_value);
            sensorName4 = itemView.findViewById(R.id.sensor_4_name);
            sensorValue4 = itemView.findViewById(R.id.sensor_4_value);
            sensorName5 = itemView.findViewById(R.id.sensor_5_name);
            sensorValue5 = itemView.findViewById(R.id.sensor_5_value);
            sensorName6 = itemView.findViewById(R.id.sensor_6_name);
            sensorValue6 = itemView.findViewById(R.id.sensor_6_value);
            sensorName7 = itemView.findViewById(R.id.sensor_7_name);
            sensorValue7 = itemView.findViewById(R.id.sensor_7_value);
            sensorName8 = itemView.findViewById(R.id.sensor_8_name);
            sensorValue8 = itemView.findViewById(R.id.sensor_8_value);
        }

        public void bind(final WalnutDevice item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }


}
