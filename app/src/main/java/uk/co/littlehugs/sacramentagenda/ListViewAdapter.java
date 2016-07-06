package uk.co.littlehugs.sacramentagenda;

/**
 * Created by hugs on 12/02/16.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends ArrayAdapter<String> {

    private DatesActivity activity;
    private List<String> datesList;
    private List<Agenda> agendaList;
    private List<Announcements> announcementsList;
    private List<Acknowledgements> acknowledgementsList;
    private List<StakeBusiness> stakeBusinessList;
    private List<WardBusiness> wardBusinessList;
    private DBHelper db;
    private String formattedDate;
    private Long lastSync;

    public ListViewAdapter(DatesActivity context, int resource, List<String> objects, List objects2) {
        super(context, resource, objects);
        this.activity = context;
        this.datesList = objects;
        if (activity.getIntent().getStringExtra("paramType").contains("Agenda")) {
            this.agendaList = objects2;
        } else if (activity.getIntent().getStringExtra("paramType").contains("Announcements")) {
            this.announcementsList = objects2;
        } else if (activity.getIntent().getStringExtra("paramType").contains("Acknowledgements")) {
            this.acknowledgementsList = objects2;
        } else if (activity.getIntent().getStringExtra("paramType").contains("Stake Business")) {
            this.stakeBusinessList = objects2;
        } else if (activity.getIntent().getStringExtra("paramType").contains("Ward Business")) {
            this.wardBusinessList = objects2;
        }
        db = new DBHelper(getContext());
lastSync = Long.parseLong(db.getLastSync(0));

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        // If holder not exist then locate all view from UI file.
        if (convertView == null) {
            // inflate UI from XML file
            convertView = inflater.inflate(R.layout.item_listview, parent, false);
            // get all UI view
            holder = new ViewHolder(convertView);
            // set tag for holder
            convertView.setTag(holder);
        } else {
            // if holder created, get tag from view
            holder = (ViewHolder) convertView.getTag();
        }


        if (activity.getIntent().getStringExtra("paramType").contains("Agenda")) {
            holder.name.setText(getFormattedDate(agendaList.get(position).getAGENDADATE()));
            Long updateDate = Long.parseLong(agendaList.get(position).getAGENDAUPDATEDATE());
            if (!db.getLastSync(0).equals("1")) {
                if (updateDate > lastSync) {
                    holder.name.setTextColor(Color.RED);
                } else {
                    holder.name.setTextColor(Color.parseColor("#0d009e"));
                }
            }
            if (agendaList.get(position).getAGENDAFIRSTSPEAKER() != null && !agendaList.get(position).getAGENDAFIRSTSPEAKER().isEmpty() &
                    agendaList.get(position).getAGENDAFINALSPEAKER() != null && !agendaList.get(position).getAGENDAFINALSPEAKER().isEmpty()) {
                holder.smallText.setText("Speakers" +"\n" + agendaList.get(position).getAGENDAFIRSTSPEAKER() + "\n" + agendaList.get(position).getAGENDASECONDSPEAKER()
                        + "\n" + agendaList.get(position).getAGENDAFINALSPEAKER());
            } else {
                holder.smallText.setText("Fast and" +"\n" + "Testimony" + "\n" + "Meeting");
            }
            holder.smallText2.setText("Prayers" + "\n" + agendaList.get(position).getAGENDAOPENINGPRAYER() + "\n" + agendaList.get(position).getAGENDACLOSINGPRAYER());
        } else if (activity.getIntent().getStringExtra("paramType").contains("Announcements")) {
            Long updateDate = Long.parseLong(announcementsList.get(position).getANNOUNCEMENTSUPDATEDATE());
            if (!db.getLastSync(0).equals("1")) {
                if (updateDate > lastSync) {
                    holder.name.setTextColor(Color.RED);
                } else {
                    holder.name.setTextColor(Color.parseColor("#0d009e"));
                }
            }
            holder.name.setText(getFormattedDate(announcementsList.get(position).getANNOUNCEMENTSDATE()));
            holder.smallText.setText(announcementsList.get(position).getANNOUNCEMENTS());
        } else if (activity.getIntent().getStringExtra("paramType").contains("Acknowledgements")) {
            Long updateDate = Long.parseLong(acknowledgementsList.get(position).getACKNOWLEDGEMENTUPDATEDATE());
            if (!db.getLastSync(0).equals("1")) {
                if (updateDate > lastSync) {
                    holder.name.setTextColor(Color.RED);
                } else {
                    holder.name.setTextColor(Color.parseColor("#0d009e"));
                }
            }
            holder.name.setText(getFormattedDate(acknowledgementsList.get(position).getACKNOWLEDGEMENTDATE()));
            holder.smallText.setText("Presiding\n" + acknowledgementsList.get(position).getACKNOWLEDGEMENTPRESIDING());
            holder.smallText2.setText("Conducting\n" + acknowledgementsList.get(position).getACKNOWLEDGEMENTCONDUCTING());
        } else if (activity.getIntent().getStringExtra("paramType").contains("Stake Business")) {
            Long updateDate = Long.parseLong(stakeBusinessList.get(position).getSTAKEBUSINESSUPDATEDATE());
            if (!db.getLastSync(0).equals("1")) {
                if (updateDate > lastSync) {
                    holder.name.setTextColor(Color.RED);
                } else {
                    holder.name.setTextColor(Color.parseColor("#0d009e"));
                }
            }
            holder.name.setText(getFormattedDate(stakeBusinessList.get(position).getSTAKEBUSINESSDATE()));
            holder.smallText.setText("\n" + stakeBusinessList.get(position).getSTAKEBUSINESS());
        } else if (activity.getIntent().getStringExtra("paramType").contains("Ward Business")) {
            Long updateDate = Long.parseLong(wardBusinessList.get(position).getWARDBUSINESSUPDATEDATE());
            if (!db.getLastSync(0).equals("1")) {
                if (updateDate > lastSync){
                    holder.name.setTextColor(Color.RED);
                } else {
                    holder.name.setTextColor(Color.parseColor("#0d009e"));
                }
            }
            holder.name.setText(getFormattedDate(wardBusinessList.get(position).getWARDBUSINESSDATE()));
            holder.smallText.setText(wardBusinessList.get(position).getWARDBUSINESSPURPOSE() + " " + wardBusinessList.get(position).getWARDBUSINESSNAME());
            holder.smallText2.setText(wardBusinessList.get(position).getWARDBUSINESSCALLING());
        }

                //handling buttons event
                holder.btnEdit.setOnClickListener(onEditListener(position, holder));
        holder.btnDelete.setOnClickListener(onDeleteListener(position, holder));
        holder.name.setOnClickListener(onEditListener(position, holder));
        holder.smallText.setOnClickListener(onEditListener(position, holder));
        holder.smallText2.setOnClickListener(onEditListener(position, holder));
        holder.linearLayout.setOnClickListener(onEditListener(position, holder));

        //convertView.findViewById(R.id.linear_layout_item).setOnClickListener(onEditListener(position, holder));

        return convertView;
    }

    private View.OnClickListener onEditListener(final int position, final ViewHolder holder) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showEditDialog(position, holder);
                if (activity.getIntent().getStringExtra("paramType").contains("Agenda")) {
                    Intent intent = new Intent(activity, AddAgendaItem.class);
                    intent.putExtra("itemPosition", datesList.get(position).toString());
                    activity.startActivity(intent);
                } else if (activity.getIntent().getStringExtra("paramType").contains("Announcements")) {
                    Intent intent = new Intent(activity, AddAnnouncementItem.class);
                    intent.putExtra("itemPosition", datesList.get(position).toString());
                    activity.startActivity(intent);
                } else if (activity.getIntent().getStringExtra("paramType").contains("Acknowledgements")) {
                    Intent intent = new Intent(activity, AddAcknowledgementsItem.class);
                    intent.putExtra("itemPosition", datesList.get(position).toString());
                    activity.startActivity(intent);
                } else if (activity.getIntent().getStringExtra("paramType").contains("Stake Business")) {
                    Intent intent = new Intent(activity, AddStakeBusinessItem.class);
                    intent.putExtra("itemPosition", datesList.get(position).toString());
                    activity.startActivity(intent);
                } else if (activity.getIntent().getStringExtra("paramType").contains("Ward Business")) {
                    Intent intent = new Intent(activity, AddWardBusinessItem.class);
                    intent.putExtra("itemPosition", datesList.get(position).toString());
                    activity.startActivity(intent);
                }
            }
        };
    }

    private void showDeleteDialog(final int position, final ViewHolder holder) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

        alertDialogBuilder.setTitle("DELETE ELEMENT?");
        final TextView delText = new TextView(activity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        delText.setText(datesList.get(position));
        if (activity.getIntent().getStringExtra("paramType").contains("Agenda")) {
            delText.setText(getFormattedDate(agendaList.get(position).getAGENDADATE()));
        } else if (activity.getIntent().getStringExtra("paramType").contains("Announcements")) {
            delText.setText(getFormattedDate(announcementsList.get(position).getANNOUNCEMENTSDATE()) + "\n" + announcementsList.get(position).getANNOUNCEMENTS());
        } else if (activity.getIntent().getStringExtra("paramType").contains("Acknowledgements")) {
            delText.setText(getFormattedDate(acknowledgementsList.get(position).getACKNOWLEDGEMENTDATE())
                    + "\nPresiding: " + acknowledgementsList.get(position).getACKNOWLEDGEMENTPRESIDING()
                    + "\nConducting: " + acknowledgementsList.get(position).getACKNOWLEDGEMENTCONDUCTING());
        } else if (activity.getIntent().getStringExtra("paramType").contains("Stake Business")) {
            delText.setText(getFormattedDate(stakeBusinessList.get(position).getSTAKEBUSINESSDATE()) + "\n" + stakeBusinessList.get(position).getSTAKEBUSINESS());
        } else if (activity.getIntent().getStringExtra("paramType").contains("Ward Business")) {
            delText.setText(getFormattedDate(wardBusinessList.get(position).getWARDBUSINESSDATE()) + "\n" + wardBusinessList.get(position).getWARDBUSINESSPURPOSE()
                    + " " + wardBusinessList.get(position).getWARDBUSINESSNAME() + " As " + wardBusinessList.get(position).getWARDBUSINESSCALLING());
        }


        delText.setLayoutParams(lp);
        alertDialogBuilder.setView(delText);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                db = new DBHelper(getContext());
                                String formattedDateTime = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.ENGLISH).format(new Date());
                                if (activity.getIntent().getStringExtra("paramType").contains("Agenda")) {
                                    Long createDate = Long.parseLong(agendaList.get(position).getAGENDA_ID());
                                    if (createDate > lastSync) {
                                        db.hardDeleteAgenda(datesList.get(position));
                                    } else {
                                        db.deleteAgenda(datesList.get(position), formattedDateTime);
                                    }
                                } else if (activity.getIntent().getStringExtra("paramType").contains("Announcements")) {
                                    Long createDate = Long.parseLong(announcementsList.get(position).getANNOUNCEMENTS_ID());
                                    if (createDate > lastSync) {
                                        db.hardDeleteAnnouncements(datesList.get(position));
                                    } else {
                                        db.deleteAnnouncements(datesList.get(position), formattedDateTime);
                                    }
                                } else if (activity.getIntent().getStringExtra("paramType").contains("Acknowledgements")) {
                                    Long createDate = Long.parseLong(acknowledgementsList.get(position).getACKNOWLEDGEMENT_ID());
                                    if (createDate > lastSync) {
                                        db.hardDeleteAcknowledgements(datesList.get(position));
                                    } else {
                                        db.deleteAcknowledgements(datesList.get(position), formattedDateTime);
                                    }
                                } else if (activity.getIntent().getStringExtra("paramType").contains("Stake Business")) {
                                    Long createDate = Long.parseLong(stakeBusinessList.get(position).getSTAKEBUSINESS_ID());
                                    if (createDate > lastSync) {
                                        db.hardDeleteStakeBusiness(datesList.get(position));
                                    } else {
                                        db.deleteStakeBusiness(datesList.get(position), formattedDateTime);
                                    }
                                } else if (activity.getIntent().getStringExtra("paramType").contains("Ward Business")) {
                                    Long createDate = Long.parseLong(wardBusinessList.get(position).getWARDBUSINESS_ID());
                                    if (createDate > lastSync) {
                                        db.hardDeleteWardBusiness(datesList.get(position));
                                    } else {
                                        db.deleteWardBusiness(datesList.get(position), formattedDateTime);
                                    }
                                }

                                holder.swipeLayout.close();
                                activity.updateAdapter(position);

                            }
                        })
                .setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog and show it
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private View.OnClickListener onDeleteListener(final int position, final ViewHolder holder) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog(position, holder);
            }
        };
    }

    private class ViewHolder {
        private TextView name;
        private TextView smallText;
        private TextView smallText2;
        private View btnDelete;
        private View btnEdit;
        private SwipeLayout swipeLayout;
        private LinearLayout linearLayout;

        public ViewHolder(View v) {
            swipeLayout = (SwipeLayout)v.findViewById(R.id.swipe_layout);
            btnDelete = v.findViewById(R.id.delete);
            btnEdit = v.findViewById(R.id.edit_query);
            name = (TextView) v.findViewById(R.id.name);
            smallText = (TextView) v.findViewById(R.id.smallText);
            smallText2 = (TextView) v.findViewById(R.id.smallText2);
linearLayout = (LinearLayout) v.findViewById(R.id.linear_layout_item);
            swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

        }
    }

    private String getFormattedDate(String trDate) {
        Date tradeDate = null;
        try {
            tradeDate = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(trDate);
            formattedDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(tradeDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  formattedDate;
    }
}