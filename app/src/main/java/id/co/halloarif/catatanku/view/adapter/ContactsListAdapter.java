package id.co.halloarif.catatanku.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

import id.co.halloarif.catatanku.R;
import id.co.halloarif.catatanku.model.ContactPickerModel;
import id.co.halloarif.catatanku.model.ContactsListPickerModel;

public class ContactsListAdapter extends BaseAdapter {
    public Context context;
    public ContactsListPickerModel contactsListPickerModel, filteredContactsListPickerModel, selectedContactsListPickerModel;
    public String filterContactName;

    public ContactsListAdapter(Context context, ContactsListPickerModel contactsListPickerModel) {

        super();
        this.context = context;
        this.contactsListPickerModel = contactsListPickerModel;
        this.filteredContactsListPickerModel = new ContactsListPickerModel();
        this.selectedContactsListPickerModel = new ContactsListPickerModel();
        this.filterContactName = "";
    }

    public void filter(String filterContactName) {

        filteredContactsListPickerModel.contactArrayList.clear();

        if (filterContactName.isEmpty() || filterContactName.length() < 1) {
            filteredContactsListPickerModel.contactArrayList.addAll(contactsListPickerModel.contactArrayList);
            this.filterContactName = "";

        } else {
            this.filterContactName = filterContactName.toLowerCase().trim();
            for (int i = 0; i < contactsListPickerModel.contactArrayList.size(); i++) {

                if (contactsListPickerModel.contactArrayList.get(i).name.toLowerCase().contains(filterContactName))
                    filteredContactsListPickerModel.addContact(contactsListPickerModel.contactArrayList.get(i));
            }
        }
        notifyDataSetChanged();

    }

    public void addContacts(ArrayList<ContactPickerModel> contacts) {
        this.contactsListPickerModel.contactArrayList.addAll(contacts);
        this.filter(this.filterContactName);

    }

    @Override
    public int getCount() {
        return filteredContactsListPickerModel.getCount();
    }

    @Override
    public ContactPickerModel getItem(int position) {
        return filteredContactsListPickerModel.contactArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(this.getItem(position).id);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            convertView = inflater.inflate(R.layout.activity_list_contact_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.chkContact = (CheckBox) convertView.findViewById(R.id.chk_contact);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.chkContact.setText(this.filteredContactsListPickerModel.contactArrayList.get(position).toString());
        viewHolder.chkContact.setId(Integer.parseInt(this.filteredContactsListPickerModel.contactArrayList.get(position).id));
        viewHolder.chkContact.setChecked(alreadySelected(filteredContactsListPickerModel.contactArrayList.get(position)));

        viewHolder.chkContact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ContactPickerModel contact = filteredContactsListPickerModel.getContact(buttonView.getId());

                if (contact != null && isChecked && !alreadySelected(contact)) {
                    selectedContactsListPickerModel.addContact(contact);
                } else if (contact != null && !isChecked) {
                    selectedContactsListPickerModel.removeContact(contact);
                }
            }
        });

        return convertView;
    }

    public boolean alreadySelected(ContactPickerModel contact) {
        if (this.selectedContactsListPickerModel.getContact(Integer.parseInt(contact.id)) != null)
            return true;

        return false;
    }

    public static class ViewHolder {

        CheckBox chkContact;
    }
}
