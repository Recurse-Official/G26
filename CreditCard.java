package mobilepaymentapplication;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CreditCard implements Parcelable {

    private int userId;
    String name;
    String cardNumber;
    String expiryDate;
    String ccvCode;

    public CreditCard(int userId, String name, String cardNumber, String expiryDate, String ccvCode) {
        this.userId = userId;
        this.name = name;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.ccvCode = ccvCode;
    }

    protected CreditCard(Parcel in) {
        userId = in.readInt();
        name = in.readString();
        cardNumber = in.readString();
        expiryDate = in.readString();
        ccvCode = in.readString();
    }

    public static final Creator<CreditCard> CREATOR = new Creator<CreditCard>() {
        @Override
        public CreditCard createFromParcel(Parcel in) {
            return new CreditCard(in);
        }

        @Override
        public CreditCard[] newArray(int size) {
            return new CreditCard[size];
        }
    };

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCcvCode() {
        return ccvCode;
    }

    public void setCcvCode(String ccvCode) {
        this.ccvCode = ccvCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(name);
        dest.writeString(cardNumber);
        dest.writeString(expiryDate);
        dest.writeString(ccvCode);
    }
}
