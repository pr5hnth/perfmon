import csv
import MySQLdb
mydb=MySQLdb.connect(host="localhost",user="root",password="",database="test")
while True:
    with open('C:\\Users\\Hp\\Desktop\\as\\LAPTOP-GLP58E12_20220329-000029\\lp.csv') as file:
        reader =csv.reader(file, delimiter=',', quotechar='"')
        data = reader.readlines()
        lastRow = data[-1]
        all_value = []
        for row in file:
            value = (row[0], row[1], row[2])
            all_value.append(value)
    query="INSERT INTO `rans`(`chek`, `chk`, `las`) VALUES (%s,%s,%s)"
    mycursor=mydb.cursor()
    mycursor.executemany(query,all_value)
    mydb.commit()
