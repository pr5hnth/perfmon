import csv
import MySQLdb
import time
while True:
    mydb=MySQLdb.connect(host="localhost",user="root",password="",database="test")
    with open('C:\\Users\\Hp\\Desktop\\as\\LAPTOP-GLP58E12_20220329-000029\\lp.csv') as csv_file:
        csvfile=csv.reader(csv_file, delimiter=",")
        all_value = []
        for row in csvfile:
            value = (row[0], row[1], row[2])
            all_value.append(value)
    query="INSERT INTO `rans`(`chek`, `chk`, `las`) VALUES (%s,%s,%s)"
    mycursor=mydb.cursor()
    mycursor.executemany(query, all_value)
    mydb.commit()
