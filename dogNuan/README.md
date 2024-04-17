# Project dogNuan
---
#### หมายเหตุ เนื่องจาก gitignore ห้าม push ่jar.file จึงเเก้ gitignore ให้สามารถ push ่jar.file หากมี jar.file อื่นอาจถูก push ขึ้นไปด้วย
## รายชื่อสมาชิกและการทำงาน
- #### นางสาวสุนีพร โภคารัตน์กุล รหัสนิสิต 6410451491
	- หน้าหลัก
		- 21.a ระบบ Create Account ของผู้ใช้งาน(user)
		- How to use กดให้แสดง PDF
	- ระบบเจ้าหน้าที่
		- 20.a มีหน้าแสดงรายการข้อมูลของวัสดุทั้งหมดในระบบ
		- 20.b มีหน้าแสดงรายการข้อมูลของครุภัณฑ์ทั้งหมดในระบบ
		- 20.c มีหน้ารายละเอียดของวัสดุ
		- 20.d มีหน้ารายละเอียดของครุภัณฑ์
        - 20.ii เงื่อนไขการเขียน CSV
		- 20.h มีหน้าแสดงรายการยื่นคำร้องขอยืม หรือขอคืนครุภัณฑ์
		- 20.i มีส่วนให้ค้นหารายการครุภัณฑ์ที่เก็บอยู่ในส่วนกลาง
	- ระบบผู้ใช้งาน
		- 22.a มีหน้าแสดงรายการครุภัณฑ์ที่อยู่ในส่วนกลาง เลือกรายการแล้วมีหน้าให้ยื่นคำร้องขอยืมโดยต้องระบุสถานที่ที่จะเก็บครุภัณฑ์
		- 22.b มีหน้าแสดงรายการครุภัณฑ์ที่อยู่ในครอบครองของตนเอง และมีปุ่มแจ้งการยื่นคำร้องขอคืนครุภัณฑ์
		- 22.c มีหน้าแสดงประวัติการเบิกวัสดุ ที่ตนเองเป็นผู้เบิก
	- อื่นๆ
		- เขียน README
		- UML Class Diagram
		- ตัวอย่างไฟล์ CSV 9.b
- #### นายมหรรณพ บรรณจิรกุล รหัสนิสิต 6410451342
	- หน้าหลัก
		- ระบบ Login
	- ระบบผู้ดูแลระบบ
		- 16.b ผู้ดูแลระบบสามารถเปลี่ยนรหัสผ่านของตนเองได้
		- 16.c มีหน้าแสดงรายชื่อของผู้ใช้ระบบ และเรียงลำดับตามวันเวลาที่เข้าใช้ระบบล่าสุดก่อน
		- 16.d มีหน้าสำหรับสร้างบัญชีผู้ใช้ของเจ้าหน้าที่
	- ระบบเจ้าหน้าที่
		- 19.b เจ้าหน้าที่สามารถเปลี่ยนรหัสผ่านของตนเองได้
		- 19.c เจ้าหน้าที่สามารถเปลี่ยนรูปภาพเพื่อใช้เป็นภาพของเจ้าหน้าที่
		- 20.g เพิ่มรูปครุภัณฑ์
	- ระบบผู้ใช้งาน
		- 21.b ผู้ใช้ระบบสามารถเปลี่ยนรหัสผ่านของตนเองได้
		- 21.c ผู้ใช้ระบบสามารถเปลี่ยนรูปภาพเพื่อใช้เป็นภาพของผู้ใช้ระบบ
	- อื่นๆ
		- สร้าง executable file
- #### นางสาววริศรา งามศรัทธา รหัสนิสิต 6410451385
	- หน้าหลัก 
		- 15.a ข้อมูลนิสิตผู้จัดทําโปรแกรม
		- 15.b ข้อมูลคําสั่งหรือคําแนะนําในการใช้โปรแกรม
	- ระบบเจ้าหน้าที่
		- 20.e มีหน้าเพิ่มข้อมูลวัสดุใหม่
		- 20.f มีหน้าจัดการข้อมูลวัสดุ   i.หน้าเพิ่มจำนวนวัสดุ   ii.หน้าเบิกพัสดุ
		- 20.g มีหน้าเพิ่มข้อมูลครุภัณฑ์ใหม่
	- UI Design
		- Graphic User Interface เกือบทั้งหมด
		- 10.e Extra i.change theme
	- อื่นๆ
		- ไฟล์ PDF 9.a 9.c 9.d
---
## Roadmap project

การส่งความคืบหน้าของ Project

- #### ครั้งที่ 1
	- วางแผนงานเบื้องต้นในการออกแบบโปรแกรม
	- ออกแบบหน้า FXML และสร้างหน้า FXML บางหน้าไว้ 
- #### ครั้งที่ 2
	- ทำระบบ Login
		- admin สามารถเข้าสู่ระบบได้
		- เพิ่มเงื่อนไขในการเข้าสู่ระบบ
	- ทำระบบสร้างบัญชี Create Account ของผู้ใช้งาน(user)
		- เพิ่มเงื่อนไขในการสร้างบัญชี
	- สร้างหน้า FXML เพิ่ม
		- หน้าเปลี่ยนรหัสผ่าน / รายการวัสดุ / รายการครุภัณฑ์ / เพิ่มวัสดุใหม่ / เพิ่มครุภัณฑ์ใหม่
- #### ครั้งที่ 3
	- มีหน้าแสดงรายละเอียดผู้พัฒนาโปรแกรม
	- ระบบ Login 
		- user และ officer สามารถเข้าสู่ระบบได้แล้ว
	- ระบบ Create Account ของผู้ใช้งาน(user)
		- เพิ่มเงื่อนไขในการสร้างบัญชีให้ครบถ้วน
	- Admin
		- เปลี่ยนรหัสผ่านของระบบ admin ได้แล้ว
		- เพิ่มเงื่อนไขการเปลี่ยนรหัสผ่าน
	- User
		- สามารถดูรายการครุภัณฑ์ และเลือกรายการเพื่อเข้าไปดูรายละเอียดครุภัณฑ์ได้
	- UI Design
		- ออกแบบ UI ใหม่ให้ง่ายต่อการใช้งาน
- ### โครงงานครั้งที่สมบูรณ์
	- สามารถกด How to use ที่หน้า Home แล้วแสดงไฟล์ PDF ที่บอกข้อมูลการใช้งานได้
	- ระบบ Create Account ของผู้ใช้งาน(user) สามารถเพิ่มรูปภาพประจำตัว upload picture
	- Admin
		- สามารถสร้างบัญชี Create Account และเพิ่มรูปประจำตัวของเจ้าหน้าที่(officer)
		- สามารถเปลี่ยนรูปภาพประจำตัวได้แล้ว
		- สามารถเห็นผู้ใช้งานทั้งหมดในระบบว่าเข้าสู่ระบบเมื่อใด และใครเข้าสู่ระบบล่าสุด User Activity
	- Officer
		- เปลี่ยนรหัสผ่านและรูปภาพของระบบ officer
		- ค้นหารายการครุภัณฑ์ได้จากการระบุชื่อครุภัณฑ์ การระบุสถานที่เก็บ การระบุชื่อผู้ครอบครอง และค้นหาจากหมวดหมู่
		- สามารถดูรายการวัสดุ และเลือกรายการเพื่อเข้าไปดูรายละเอียดวัสดุ
			- มีตารางประวัติรายการเพิ่มจำนวนวัสดุ
			- มีตารางประวัติรายการเบิกของผู้ใช้งาน
		- สามารถเพิ่มจำนวนวัสดุได้ในหน้ารายละเอียดวัสดุ
		- สามารถเลือกผู้เบิกและจำนวนที่จะเบิกได้ในหน้ารายละเอียดวัสดุโดย Go to Withdraw
		- สามารถเลือกรายการคำร้องขอยืม/คืนของครุภัณฑ์ และกดอนุมัติได้
		- เพิ่มครุภัณฑ์ใหม่ และเพิ่มรูปครุภัณฑ์ได้
		- เพิ่มวัสดุใหม่ได้
	- User
		- เปลี่ยนรหัสผ่านและรูปภาพของระบบ user
		- สามารถกรอกสถานที่เก็บ และยื่นคำร้องขอยืมครุภัณฑ์ได้ที่หน้ารายละเอียดครุภัณฑ์
		- ค้นหารายการครุภัณฑ์ได้จากการระบุชื่อครุภัณฑ์ การระบุสถานที่เก็บ การระบุชื่อผู้ครอบครอง และค้นหาจากหมวดหมู่
		- ดูประวัติรายการยืมครุภัณฑ์ และเลือกรายการในการยื่นคำร้องขอคืนครุภัณฑ์
		- ดูประวัติรายการเบิกวัสดุได้
	- UI Design
		- ออกแบบหน้า UI เพิ่ม
		- สามารถเปลี่ยนธีมได้แล้ว
	- สร้าง UML Diagram
	- ทำไฟล์ PDF และ README
	- สร้าง executable file
---
## วิธี run โปรแกรม
เข้าไปที่โฟลเดอร์ ProjectdogNuan และดับเบิ้ลคลิกที่โปรแกรม dogNuanApplication.jar

---
## การวางโครงสร้างไฟล์
```
├── data
│    ├── Durable_articles.csv
│    ├── Materials.csv
│    ├── Materials_borrow.csv
│    ├── Materials_log.csv
│    ├── RequestBorrow.csv
│    ├── RequestReturn.csv
│    └── User.csv
│
├── images(เก็บไฟล์ภาพภายนอกโปรเจค)
│
└── src
     └── main
          ├── java
          │    ├── dept
          │    │    └── app
          │    │         ├── controllers
          │    │         │    ├── admin
          │    │         │    │    ├── AdminChangeProfileController
          │    │         │    │    ├── AdminController
          │    │         │    │    └── CreateOfficeAccount
          │    │         │    │    
          │    │         │    ├── home
          │    │         │    │    ├── AboutUsController
          │    │         │    │    ├── CreateUserAccount
          │    │         │    │    └── HomeController
          │    │         │    │    
          │    │         │    ├── officer
          │    │         │    │    ├── AddDurablesController
          │    │         │    │    ├── AddMeterialsController
          │    │         │    │    ├── BorrowReturnController
          │    │         │    │    ├── DurableDetail
          │    │         │    │    ├── DurableTableController
          │    │         │    │    ├── MaterialDetail
          │    │         │    │    ├── MaterialTableController
          │    │         │    │    ├── MaterialWithdrawController
          │    │         │    │    ├── OfficerChangeProfileController
          │    │         │    │    └── OfficerController
          │    │         │    │    
          │    │         │    ├── user
          │    │         │    │    ├── DurableRequestBorrow
          │    │         │    │    ├── DurablesListController
          │    │         │    │    ├── HistoryDurable
          │    │         │    │    ├── HistoryMaterial
          │    │         │    │    ├── UserChangeProfileController
          │    │         │    │    └── UserController
          │    │         │    │    
          │    │         │    └── HelloController
          │    │         │
          │    │         ├── models
          │    │         │    ├── Durable
          │    │         │    ├── DurableList
          │    │         │    ├── Material
          │    │         │    ├── MaterialList
          │    │         │    ├── MaterialLog
          │    │         │    ├── MaterialLogList
          │    │         │    ├── MaterialWithdraw
          │    │         │    ├── MaterialWithdrawList
          │    │         │    ├── Object
          │    │         │    ├── RequestBorrow
          │    │         │    ├── RequestBorrowList
          │    │         │    ├── RequestReturn
          │    │         │    ├── RequestReturnList
          │    │         │    ├── User
          │    │         │    └── UserList
          │    │         │    
          │    │         ├── services
          │    │         │    ├── BorrowListFileDatasource
          │    │         │    ├── Datasource
          │    │         │    ├── DurableListFileDatasource
          │    │         │    ├── FXRouter
          │    │         │    ├── MaterialAmountAddDatasource
          │    │         │    ├── MaterialListFileDatasource
          │    │         │    ├── MaterialWithdrawDatasource
          │    │         │    ├── ReturnListFileDatasource
          │    │         │    ├── SortTimeUser
          │    │         │    ├── UserFileDatasource
          │    │         │    └── UserHardCodeDatasource.java
          │    │         │
          │    │         ├── Main
          │    │         └── MainApplication
          │    │
          │    └── module-info.java
          │    
          └── resources
               ├── dept
               │    └── app
               │         ├── Theme
               │         │    ├── Font
               │         │    │    ├── Kanit-Light.ttf
               │         │    │    ├── Kanit-Regular.ttf
               │         │    │    └── Kanit-SemiBold.ttf
               │         │    │    
               │         │    ├── dark.css
               │         │    └── pastel.css
               │         │    
               │         └── views
               │              ├── about_us.fxml
               │              ├── add_durables.fxml
               │              ├── add_materials.fxml
               │              ├── admin_change_profile.fxml
               │              ├── admin_page.fxml
               │              ├── borrow_return.fxml
               │              ├── create_officer_account.fxml
               │              ├── create_user_account.fxml
               │              ├── hello-view.fxml
               │              ├── history_durable.fxml
               │              ├── history_material.fxml
               │              ├── home.fxml
               │              ├── material_withdraw.fxml
               │              ├── officer_change_profile.fxml
               │              ├── officer_durabledetail.fxml
               │              ├── officer_durablelist.fxml
               │              ├── officer_materialdetail.fxml
               │              ├── officer_materiallist.fxml
               │              ├── officer_page.fxml
               │              ├── user_borrowdurable.fxml
               │              ├── user_change_profile.fxml
               │              ├── user_durablelist.fxml
               │              └── user_page.fxml
               │
               └── image (เก็บไฟล์ภาพภายในโปรเจค)
```
---
## ตัวอย่างของผู้ใช้งานใน program
### User
- Username : Bobs
- Password : bob_minion

### Officer
- Username : Gift
- Password : minion_03

### Admin
- Username : Grus
- Password : admin_01