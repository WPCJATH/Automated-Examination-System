
import random

subject_code = ["COMP1001","COMP1002","COMP1003","COMP1004","COMP1005","COMP1006","COMP1007","COMP1008"]
subject_topic = ["Problem Solving","Freshman Seminar","Programming Fundementals","Data Analysis","Object-Oriented Programming","Database Systems","Data Structure","Computer Systems"]

Class = ["C001","C002","C003","C004","C005"]



Depart = "COMP"

Password = "123456"

state1 = "Active"
state2 = "Graduated"
State = [state1,state1,state1,state1,state2]

 
# teacher  staff_id,name,password,email,depart
# student  stu_id,name,password,class_no.,email,depart
# subject  subject_code,topic
# class    class_no.,staff_id,state                               
# has      subject_code,class_no.,staff_id                               subject <---> teacher <---> class


# 5 class teacher
# 16 teachers                 2 teachers per subject
# 30 * 5 students            30 students per class
# 8 subject                   6 subjects per class
# up to 30 exams
# 

# order ##
# subject
# teacher
# class
# student
# has
# exam
# question
# answer
# record

def main():
    staff_ids = teacher()
    sub()
    class_(staff_ids)
    stu()
    has(staff_ids)
    #sub_has_class = has()
    #sub_taughtby_tea = teach(staff_ids)
    #deliver(sub_has_class,sub_taughtby_tea)

# teacher  staff_id,name,password,email,depart
def teacher():
    global Password
    n = 16
    staff_id = get_id(n,'s')
    name = get_names(n)
    password = [Password]*n
    email = get_email(n)
    depart = [Depart]*n
    Print([staff_id,name,password,email,depart],"teacher")
    return staff_id

# subject  subject_code,topic
def sub():
    global subject_code
    global subject_topic
    Print([subject_code,subject_topic],"subject")


# class    class_no.,staff_id,state     
def class_(ids):
    global Class
    global State
    staff_ids = list()
    while len(staff_ids) != len(Class):
        index = random.randint(0,len(ids)-1)
        if ids[index] not in staff_ids:
            staff_ids.append(ids[index])
    
    Print([Class,staff_ids,State],"class")



# student  stu_id,name,password,class_no.,email,depart
def stu():
    global Class
    global Depart
    global Password
    n = 150
    stu_id = get_id(n,'d')
    name = get_names(n)
    password = [Password]*n
    class_no = Class*(int(n/len(Class)))
    email = get_email(n)
    depart = [Depart]*n
    Print([stu_id,name,password,class_no,email,depart],"student")

def has(staff_ids):
    teachers = []
    for i in staff_ids:
        teachers.append(i)
        teachers.append(i)
    teachers.remove(teachers[-1])
    teachers.remove(teachers[-1])
    teachers[-1] = staff_ids[-1]

    subjects = subject_code*4
    subjects.remove(subjects[-1])
    subjects.remove(subjects[-1])

    classes = Class*6

    Print([subjects,classes,teachers],"has")

    '''for i in range(30):
        print(subjects[i],classes[i],teachers[i])'''




'''# has      subject_code,class_no.    
def has():
    global subject_code
    global Class
    n = 6  # n subjects per class
    subjects = []
    classes = []
    for i in Class:
        c = []
        s = []
        for j in range(n):
            c.append(i)
            index = random.randint(0,len(subject_code)-1)
            while subject_code[index] in s:
                index = random.randint(0,len(subject_code)-1)
            s.append(subject_code[index])
        subjects.append(s)
        classes.append(c)
    
    sfors = []
    cfors = []
    for i in subjects:
        for j in i:
            sfors.append(j)

    for i in classes:
        for j in i:
            cfors.append(j)

    Print([sfors,cfors],'has')

    sub_has_class = dict()
    for i in subject_code:
        for j in subjects:
            if i in j:
                if i in sub_has_class:
                    sub_has_class[i].append(classes[subjects.index(j)][0])
                else:
                    ls = []
                    ls.append(classes[subjects.index(j)][0])
                    sub_has_class.update({i:ls}) 

    return sub_has_class

# teach    subject_code,staff_id 
def teach(ids):
    global subject_code
    n = 2 # n teacher per subject

    subjects = subject_code*n

    Print([subjects,ids],'teach')

    sub_taughtby_tea = dict()

    for i in range(len(subjects)):
        if subjects[i] in sub_taughtby_tea:
            sub_taughtby_tea[subjects[i]].append(ids[i])
        else:
            ls = []
            ls.append(ids[i])
            sub_taughtby_tea.update({subjects[i]:ls})
    
    return sub_taughtby_tea


# deliver  staff_id,class_no.    
def deliver(sub_has_class,sub_taughtby_tea):
    lecKey=[] # [sub,class]: staff
    lecValue=[] # manually dict

    for sub in sub_has_class:
        n = 0
        for cl in sub_has_class[sub]:
            ls = []
            ls.append(sub)
            ls.append(cl)
            lecKey.append(ls)
            lecValue.append(sub_taughtby_tea[sub][n])
            if n+1 < len(sub_taughtby_tea[sub]): n+=1
            else: n = 0

    classes = []
    teachers = []

    for i in range(len(lecKey)):
        classes.append(lecKey[i][1])
        teachers.append(lecValue[i])

    Print([teachers,classes],"deliver")'''








def Print(ls,name):
    f = open(name+".sql","w" ) 
    strForprint = ''
    for i in range(len(ls[0])):
        strForprint += "insert into "+name+'\n'+"values (\n"
        strForprint += "'"+ls[0][i]+"'"
        for j in range(1,len(ls)):
            strForprint += ",'"+ls[j][i]+"'"
        strForprint += "\n);\n"  
    f.write(strForprint)
    f.close()
    


def get_names(x):
    firstname = ['Emily', 'Michael', 'Hannah', 'Joshua', 'Madison', 'Matthew', 'Samantha', 'Andrew', 'Ashley', 'Joseph', 'Sarah', 'Nicholas', 'Elizabeth', 'Anthony', 'Kayla', 'Tyler', 'Alexis', 'Daniel', 'Abigail', 'Christopher', 'Jessica', 'Alexander', 'Taylor', 'John', 'Anna', 'William', 'Lauren', 'Brandon', 'Megan', 'Dylan', 'Brianna', 'Zachary', 'Olivia', 'Ethan', 'Victoria', 'Ryan', 'Emma', 'Justin', 'Grace', 'David', 'Rachel', 'Benjamin', 'Jasmine', 'Christian', 'Nicole', 'Austin', 'Destiny', 'Cameron', 'Alyssa', 'James', 'Chloe', 'Jonathan', 'Julia', 'Logan', 'Jennifer', 'Nathan', 'Kaitlyn', 'Samuel', 'Morgan', 'Hunter', 'Isabella', 'Noah', 'Natalie', 'Robert', 'Alexandra', 'Jose', 'Sydney', 'Thomas', 'Katherine', 'Kyle', 'Amanda', 'Kevin', 'Stephanie', 'Gabriel', 'Hailey', 'Elijah', 'Maria', 'Jason', 'Gabrielle', 'Luis', 'Haley', 'Aaron', 'Rebecca']
    secondname = ['Corbyn', 'Smith', 'Jones', 'Williams', 'Brown', 'Taylor', 'Davies', 'Wilson', 'Evans', 'Thomas', 'Johnson', 'Roberts', 'Walker', 'Wright', 'Thompson', 'Robinson', 'White', 'Hughes', 'Edwards', 'Hall', 'Green', 'Martin', 'Wood', 'Lewis', 'Harris', 'Clarke', 'Jackson', 'Clark', 'Turner', 'Scott', 'Hill', 'Moore', 'Cooper', 'Ward', 'Morris', 'King', 'Watson', 'Harrison', 'Morgan', 'Baker', 'Young', 'Patel', 'Allen', 'Anderson', 'Mitchell', 'Phillips', 'James', 'Campbell', 'Bell', 'Lee', 'Kelly', 'Parker', 'Davis', 'Bennett', 'Miller', 'Price', 'Shaw', 'Cook', 'Simpson', 'Griffiths', 'Richardson', 'Stewart', 'Marshall', 'Collins', 'Carter', 'Bailey', 'Murphy', 'Gray', 'Murray', 'Cox', 'Adams', 'Richards', 'Graham', 'Ellis', 'Wilkinson', 'Foster', 'Robertson', 'Chapman', 'Russell', 'Mason', 'Webb', 'Powell', 'Rogers', 'Gibson', 'Hunt', 'Holmes']
    names = []

    for i in range(x):
        names.append(firstname[random.randint(0,len(firstname)-1)]+', '+secondname[random.randint(0,len(secondname)-1)])

    return names


def get_email(x):
    tial = ["@icloud.com","@gmail.com","@connect.abcu.hk","@outlook.com","@163.com"]
    emails = []

    for i in range(x):

        length = random.randint(5,10)
        emial = ''

        for i in range(length):
            emial += chr(random.randint(97,122))

        emails.append(emial+tial[random.randint(0,len(tial)-1)])

    return emails


def get_id(x,s):
    ids = []
    
    while len(ids) < x:

        if s == 'd': id = '19'
        else: id = '80'

        for j in range(6):
            id += str(random.randint(0,9))

        if (id+s) not in ids:
            ids.append(id+s)
    
    return ids


main()