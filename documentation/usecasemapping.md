# Use Case Mapping

## Use Case: Select Courses To Teach
- Description: Allows users to select courses and specific sections to teache.
- Actors: Professor
- Flow:
  1. Professor opens the attendance tracker website
  2. Professor log in
  3. Professor views the list of available courses and sections
  4. Professor selects a specific course-section to teach

### Mapped User Stories
1. As a professor, I want to be able to choose a section to teach.

## Use Case: Browse Course Attendance Record
- Description: Allows users to view attendance records for specific courses and sections.
- Actors: Professor, Student
- Flow:
  1. User opens the attendance tracker website.
  2. User logs in.
  3. Professor selects a section and a date to see students' attendance status. Student selects a section and see their own attendance status.
  4. User views the attendance records.


### Mapped User Stories
1. As a professor, I want to be able to see attendance records for my courses to monitor student participation.
2. As a student, I want to view attendance records to ensure compliance with attendance policies.

## Use Case: Change Attendance Record
- Description: Allows users to modify attendance records for specific course sessions.
Actors: Professor
- Flow:
  1. Professor logs into the attendance tracker.
  2. Professor navigates to a specific course and session.
  3. Professor modifies the attendance details of certain students.
  4. System updates and saves the new attendance information.


### Mapped User Stories
1. As a professor, I want to correct any mistakes in the attendance record after a class session. Some students might be tardy instead of absent.

## Use Case: Receive Attendance Report
- Description: Users receive automated attendance reports for their courses at regular intervals. They can also ask for specific report.
- Actors: Professor, Student
- Flow:
1. System generates attendance reports based on predefined schedules.
2. System sends reports to professors and students via email.
3. Users review the attendance report received.

### Mapped User Stories
1. As a professor, I want to receive regular attendance reports to keep track of overall class attendance.
2. As a student, I want to receive attendance reports to review attendance trends and ask for any change.


## Use Case: Add courses
- Description: Allows administrators to add new courses into the system.
- Actors: Administrator
- Flow:
  1. Administrator logs into the system.
  2. Administrator accesses the course management section.
  3. Administrator enters details for the new course and saves it.
  4. System confirms the addition and updates the course list.
### Mapped User Stories
1. As an administrator, I want to add new courses to provide more options for students and faculty.

## Use Case: Add Users
- Description: Allows administrators to add new users to the system, such as students, professors, and staff.
- Actors: Administrator
- Flow:
  1. Administrator logs into the administrative panel.
  2. Administrator selects the option to add new users.
  3. Administrator enters the user details and assigns roles.
  4. System creates the new user account and confirms the addition.
### Mapped User Stories
1. As an administrator, I want to be able to add new users to ensure they can access and utilize the system appropriately.


## Use Case:  Browse Change Profile
- Description: Allows professors and students to view their basic information and change what they want to display.
- Actors: Professor, Student
- Flow:
  1. User logs into the attendance tracker website.
  2. User press profile button.
  3. User view profile information.
  4. USer choose whether to change display name and notification preferrence or not.
### Mapped User Stories
1. As a User, I want to view my own bio info and change the way of notification message receiving.
