package id.co.halloarif.catatanku.model;

import java.util.ArrayList;

public class ContactsListPickerModel {
    public ArrayList<ContactPickerModel> contactArrayList;

    public ContactsListPickerModel() {

        contactArrayList = new ArrayList<ContactPickerModel>();
    }

    public int getCount() {

        return contactArrayList.size();
    }

    public void addContact(ContactPickerModel contact) {
        contactArrayList.add(contact);
    }

    public void removeContact(ContactPickerModel contact) {
        contactArrayList.remove(contact);
    }

    public ContactPickerModel getContact(int id) {

        for (int i = 0; i < this.getCount(); i++) {
            if (Integer.parseInt(contactArrayList.get(i).id) == id)
                return contactArrayList.get(i);
        }

        return null;
    }
}
