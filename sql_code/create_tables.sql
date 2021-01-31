CREATE TABLE `teacher` (
  `staff_id` char(9) not null,
  `name` char(25) not null,
  `password` char(16) not null,
  `email` char(30),
  `depart` char(10) not null,
  PRIMARY KEY (`staff_id`)
);

CREATE TABLE `subject` (
  `subject_code` char(8) not null,
  `topic` char(100) not null,
  PRIMARY KEY (`subject_code`)
);

CREATE TABLE `class` (
  `class_no.` char(5) not null,
  `staff_id` char(9),
  `state` enum('Active','Graduated') not null default 'Active',
  PRIMARY KEY (`class_no.`),
  FOREIGN KEY (`staff_id`) REFERENCES `teacher`(`staff_id`)
);

CREATE TABLE `student` (
  `stu_id` char(9),
  `name` char(25) not null,
  `password` char(16) not null,
  `class_no.` char(5) not null,
  `email` char(30),
  `depart` char(10),
  PRIMARY KEY (`stu_id`),
  FOREIGN KEY (`class_no.`) REFERENCES `class`(`class_no.`)
);

CREATE TABLE `exam` (
  `exam_code` char(5),
  `exam_name` char(20) default '',
  `subject_code` char(8) not null,
  `date` char(10) not null,
  `start_time` char(8) not null,
  `due_time` char(8) not null,
  `full_mark` int not null default 0,
  `class_no.` char(5) not null,
  `description` TEXT,
  PRIMARY KEY (`exam_code`),
  FOREIGN KEY (`subject_code`) REFERENCES `subject`(`subject_code`),
  FOREIGN KEY (`class_no.`) REFERENCES `class`(`class_no.`)
);

CREATE TABLE `question` (
  `exam_code` char(5),
  `question_no.` int,
  `stand_answer` TEXT,
  `full_mark` int not null default 0,
  `flag` enum('Compulsory','Optional') not null default 'Compulsory',
  `type` enum('Multiple-choices','Fill in blank','Standard full-length') not null default 'Multiple-choices',
  `text` TEXT not null,
  `audio` LONGBLOB,
  `graphic` LONGBLOB,
  PRIMARY KEY (`question_no.`, `exam_code`),
  FOREIGN KEY (`exam_code`) REFERENCES `exam`(`exam_code`)
);

CREATE TABLE `answer` (
  `stu_id` char(9),
  `exam_code` char(5),
  `question_no.` int,
  `content` TEXT,
  `mark` int not null default -1,
  `comment` TEXT,
  PRIMARY KEY (`stu_id`, `exam_code`, `question_no.`),
  FOREIGN KEY (`question_no.`) REFERENCES `question`(`question_no.`),
  FOREIGN KEY (`exam_code`) REFERENCES `exam`(`exam_code`),
  FOREIGN KEY (`stu_id`) REFERENCES `student`(`stu_id`)
);

CREATE TABLE `record` (
  `stu_id` char(9),
  `exam_code` char(5),
  `date` char(10) not null,
  `letter_grade` char(2),
  PRIMARY KEY (`stu_id`, `exam_code`),
  FOREIGN KEY (`stu_id`) REFERENCES `student`(`stu_id`),
  FOREIGN KEY (`exam_code`) REFERENCES `exam`(`exam_code`)
);


CREATE TABLE `has` (
  `subject_code` char(8),
  `class_no.` char(5),
   `staff_id` char(9),
  PRIMARY KEY (`subject_code`, `class_no.`, `staff_id`),
  FOREIGN KEY (`subject_code`) REFERENCES `subject`(`subject_code`),
  FOREIGN KEY (`class_no.`) REFERENCES `class`(`class_no.`),
  FOREIGN KEY (`staff_id`) REFERENCES `teacher`(`staff_id`)
);



