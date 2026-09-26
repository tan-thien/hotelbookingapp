# 🏨 Ứng Dụng Quản Lý Và Đặt Phòng Khách Sạn (Hotel Booking & Management App)

Ứng dụng di động được phát triển trên nền tảng Android (Java), tích hợp hệ quản trị cơ sở dữ liệu thời gian thực **Firebase (Realtime Database, Authentication, Storage)**, hỗ trợ đầy đủ các tính năng cho cả hai đối tượng: **Khách hàng** (Đặt phòng, dịch vụ, xem lịch sử) và **Quản trị viên** (Quản lý phòng, loại phòng, dịch vụ, voucher, thống kê doanh thu).

---

## 🚀 Các Tính Năng Chính

### 🔐 1. Xác Thực & Người Dùng (Authentication)
*   **Đăng nhập & Đăng ký:** Hỗ trợ đăng nhập bằng tài khoản/mật khẩu truyền thống (`Login.java`, `register.java`).
*   **Xác thực qua Số điện thoại:** Đăng nhập bằng số điện thoại kết hợp xác thực mã OTP (`LoginSdtActivity.java`, `OTPVerificationActivity.java`).
*   **Quản lý tài khoản:** Xem và cập nhật thông tin cá nhân khách hàng (`AccountFragment.java`, `Users.java`).

### 🛌 2. Chức Năng Dành Cho Khách Hàng (Customer Features)
*   **Trang chủ (`HomeFragment.java`):** Hiển thị danh sách các loại phòng và phòng trống nổi bật, thanh tìm kiếm.
*   **Chi tiết phòng (`DetailRoomFragment.java`, `detailRCusFragment.java`):** Xem thông tin mô tả chi tiết phòng, giá cả, tiện ích và hình ảnh trực quan.
*   **Đặt phòng (`BookingFragment.java`, `Booking.java`):** Chọn ngày nhận/trả phòng, tính toán chi phí, áp dụng mã giảm giá và tiến hành đặt phòng.
*   **Dịch vụ đi kèm (`ServiceFragment.java`, `DetailSCusFragment.java`):** Khách hàng có thể tìm kiếm và đặt thêm các dịch vụ tiện ích của khách sạn (như ăn uống, giặt là, spa...).
*   **Giỏ hàng dịch vụ (`CartFragment.java`):** Quản lý các dịch vụ đã chọn trước khi thanh toán.
*   **Thanh toán (`ThanhToanFragment.java`):** Hỗ trợ tính toán tổng chi phí (Phòng + Dịch vụ - Voucher) và xử lý quy trình thanh toán.
*   **Lịch sử đặt phòng (`HistoryFragment.java`, `BookingHistoryAdapter.java`):** Cho phép người dùng theo dõi danh sách các đơn đặt phòng đã thực hiện, trạng thái đơn hàng.

### 👔 3. Chức Năng Dành Cho Quản Trị Viên (Admin Management)
*   **Quản lý Phòng (`qlroomFragment.java`, `uploadRoomFragment.java`, `updateRoomFragment.java`):** Thêm mới phòng, cập nhật trạng thái phòng (Trống/Đã đặt/Đang sửa chữa) hoặc xóa phòng.
*   **Quản lý Loại phòng (`qlRoomTypeFragment.java`, `uploadRoomtypeFragment.java`):** Phân loại phòng (VIP, Deluxe, Standard, Family...) và thiết lập cấu hình giá cơ bản.
*   **Quản lý Dịch vụ (`qlserviceFragment.java`, `uploadServiceFragment.java`, `DetailServiceFragment.java`):** Quản lý danh mục các dịch vụ khách sạn cung cấp.
*   **Quản lý Khách hàng (`qlcustomerFragment.java`):** Xem danh sách và quản lý thông tin các tài khoản người dùng trong hệ thống.
*   **Quản lý Đơn đặt phòng (`qlbookingFragment.java`):** Tiếp nhận, phê duyệt hoặc hủy các yêu cầu đặt phòng từ khách hàng.
*   **Quản lý Khuyến mãi (`qlvoucherFragment.java`):** Tạo và quản lý các mã giảm giá (Voucher) áp dụng cho việc đặt phòng.
*   **Thống kê & Báo cáo (`ThongKeDoanhThuFragment.java`):** Biểu đồ hoặc số liệu trực quan báo cáo doanh thu theo thời gian, giúp admin dễ dàng theo dõi hiệu quả kinh doanh.

---

## 🛠️ Công Nghệ Sử Dụng

*   **Ngôn ngữ lập trình:** Java (Android SDK)
*   **Kiến trúc UI:** Fragments & Activities kết hợp ViewBinding giúp tối ưu hiệu năng giao diện (`viewBinding = true`).
*   **Cơ sở dữ liệu & Back-end:** Firebase Ecosystem
    *   *Firebase Authentication:* Quản lý đăng ký/đăng nhập (Mật khẩu & OTP SMS).
    *   *Firebase Realtime Database:* Lưu trữ đồng bộ thời gian thực dữ liệu phòng, dịch vụ, hóa đơn và người dùng.
    *   *Firebase Storage:* Lưu trữ hình ảnh phòng và dịch vụ chất lượng cao.
*   **Thư viện bên thứ ba:**
    *   *Glide (4.16.0):* Tải và cache hình ảnh mượt mà từ Firebase Storage.
    *   *Clans Floating Action Button (FAB):* Tối ưu hóa các nút chức năng nhanh (Thêm, Sửa, Xóa).
    *   *Navigation Component:* Quản lý luồng chuyển màn hình mượt mà giữa các Fragments.

---

## 📁 Cấu Trúc Mã Nguồn Chính (`com.example.doan`)

```text
├── Main/Authentication Activities
│   ├── MainActivity.java             # Điều hướng chính ứng dụng
│   ├── Login.java / register.java   # Đăng nhập, đăng ký tài khoản
│   ├── LoginSdtActivity.java        # Đăng nhập qua Số điện thoại
│   └── OTPVerificationActivity.java  # Xác thực OTP SMS
│
├── Customer Fragments (Chức năng khách hàng)
│   ├── HomeFragment.java             # Màn hình chính xem phòng
│   ├── DetailRoomFragment.java       # Chi tiết thông tin phòng
│   ├── BookingFragment.java          # Giao diện đặt phòng
│   ├── CartFragment.java             # Giỏ hàng đặt dịch vụ
│   ├── ThanhToanFragment.java        # Giao diện tính toán & thanh toán
│   └── HistoryFragment.java          # Lịch sử đặt phòng khách hàng
│
├── Admin Fragments (Chức năng quản trị)
│   ├── qlroomFragment.java           # Quản lý danh sách phòng khách sạn
│   ├── qlRoomTypeFragment.java       # Quản lý danh mục loại phòng
│   ├── qlserviceFragment.java        # Quản lý danh mục các dịch vụ
│   ├── qlvoucherFragment.java        # Quản lý danh sách mã giảm giá
│   ├── qlbookingFragment.java        # Quản lý các đơn đặt phòng cần duyệt
│   └── ThongKeDoanhThuFragment.java  # Thống kê doanh thu khách sạn
│
└── Adapters & Data Classes
    ├── DataClass.java / Users.java   # Các model định nghĩa dữ liệu mẫu
    ├── MyAdapter.java                # Adapter tổng hợp danh sách phòng
    ├── roomtypeAdapter.java          # Adapter hiển thị loại phòng
    └── BookingHistoryAdapter.java    # Adapter hiển thị lịch sử đặt phòng
```

---

## ⚙️ Yêu Cầu Hệ Thống & Cài Đặt

1.  **Môi trường phát triển:** Android Studio (Koala hoặc mới hơn).
2.  **Gradle & AGP Version:** Gradle `8.9` / Android Gradle Plugin `8.7.3`.
3.  **SDK tối thiểu (Min SDK):** Android API 26 (Android 8.0 Oreo).
4.  **Target SDK:** Android API 34 (Android 14).
5.  **Cài đặt Firebase:** 
    *   Tải file `google-services.json` từ Firebase Console của bạn.
    *   Đặt file vào thư mục `/app`.
    *   Bật tính năng *Email/Password Login* và *Phone Auth* trong Firebase Authentication.
    *   Cấu hình Rules của *Realtime Database* và *Storage* sang chế độ cho phép đọc/ghi thích hợp.
