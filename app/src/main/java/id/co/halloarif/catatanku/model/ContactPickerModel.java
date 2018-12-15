package id.co.halloarif.catatanku.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ContactPickerModel implements Parcelable {
    public static final Creator<ContactPickerModel> CREATOR = new Creator<ContactPickerModel>() {
        @Override
        public ContactPickerModel createFromParcel(Parcel in) {
            return new ContactPickerModel(in);
        }

        @Override
        public ContactPickerModel[] newArray(int size) {
            return new ContactPickerModel[size];
        }
    };
    public String id, name, phone, label;

    public ContactPickerModel() {
    }

    public ContactPickerModel(String id, String name, String phone, String label) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.label = label;
    }

    protected ContactPickerModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        phone = in.readString();
        label = in.readString();
    }

    @Override
    public String toString() {
        return name + " | " + label + " : " + phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(label);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
