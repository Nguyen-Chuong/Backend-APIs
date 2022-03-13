package com.capstone_project.hbts.constants;

public class ErrorConstant {

    /**
     * Có lỗi xảy ra trong quá trình xử lý
     */
    public static final String ERR_000 = "ERR_000";
    public static final String ERR_000_LABEL = "An error has occurred";

    /**
     * Mật khẩu cũ không tồn tại
     */
    public static final String ERR_USER_001 = "ERR_USER_001";
    public static final String ERR_USER_001_LABEL = "Old password is not exist";

    /**
     * Mật khẩu không đúng
     */
    public static final String ERR_USER_002 = "ERR_USER_002";
    public static final String ERR_USER_002_LABEL = "Incorrect Password";

    /**
     * Email không đúng
     */
    public static final String ERR_USER_003 = "ERR_USER_003";
    public static final String ERR_USER_003_LABEL = "Incorrect Email";

    /**
     * Email đã tồn tại
     */
    public static final String ERR_USER_004 = "ERR_USER_004";
    public static final String ERR_USER_004_LABEL = "This email is already taken";

    /**
     * Username đã tồn tại
     */
    public static final String ERR_USER_005 = "ERR_USER_005";
    public static final String ERR_USER_005_LABEL = "This username is already taken";

    /**
     * Username không tồn tại
     */
    public static final String ERR_USER_006 = "ERR_USER_006";
    public static final String ERR_USER_006_LABEL = "Username not found";

    /**
     * Feedback chưa được phản hồi
     */
    public static final String ERR_USER_007 = "ERR_USER_007";
    public static final String ERR_USER_007_LABEL = "Feedback has not been answered";

    /**
     * Tài khoản của bạn đã bị xóa
     */
    public static final String ERR_USER_008 = "ERR_USER_008";
    public static final String ERR_USER_008_LABEL = "Your account has been disabled";

    /**
     * Ngày đến phải trước ngày đi
     */
    public static final String ERR_USER_009 = "ERR_USER_009";
    public static final String ERR_USER_009_LABEL = "Your date in have to before date out";

    /**
     * Không tìm thấy Otp
     */
    public static final String ERR_OTP_001 = "ERR_OTP_001";
    public static final String ERR_OTP_001_LABEL = "OTP Not Found";

    /**
     * OTP hết hạn
     */
    public static final String ERR_OTP_002 = "ERR_OTP_002";
    public static final String ERR_OTP_002_LABEL = "OTP is expired";

    /**
     * Mã xác minh OTP sai
     */
    public static final String ERR_OTP_003 = "ERR_OTP_003";
    public static final String ERR_OTP_003_LABEL = "OTP is incorrect";

    /**
     * Dữ liệu cần giải mã không hợp lệ
     */
    public static final String ERR_DATA_001 = "ERR_DATA_001";
    public static final String ERR_DATA_001_LABEL = "Your data url decrypted is not valid";

    /**
     * Không thể gửi tiếp request
     */
    public static final String ERR_REQ_001 = "ERR_REQ_001";
    public static final String ERR_REQ_001_LABEL = "You cannot request again, your request is pending or accepted";

    /**
     * Không thể hủy request
     */
    public static final String ERR_REQ_002 = "ERR_REQ_002";
    public static final String ERR_REQ_002_LABEL = "You cannot cancel request, it has been processed";


    /**
     * Khách sạn này của bạn đã bị cấm đăng bài
     */
    public static final String ERR_HOTEL_001 = "ERR_HOTEL_001";
    public static final String ERR_HOTEL_001_LABEL = "Your hotel is banned, you cannot request again";

    /**
     * Khách sạn chưa đủ điều kiện request
     */
    public static final String ERR_HOTEL_002 = "ERR_HOTEL_002";
    public static final String ERR_HOTEL_002_LABEL = "Your hotel is not qualified for requesting, " +
            "add at least one room type for booking";

    /**
     * Không thể thêm trùng item
     */
    public static final String ERR_ITEM_001 = "ERR_ITEM_001";
    public static final String ERR_ITEM_001_LABEL = "You cannot add duplicate item";

    /**
     * Item này đã tồn tại trong danh sách
     */
    public static final String ERR_ITEM_002 = "ERR_ITEM_002";
    public static final String ERR_ITEM_002_LABEL = "You already have this item in your list";

}
