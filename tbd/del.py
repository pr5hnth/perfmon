import pandas as pd
import MySQLdb
mydb=MySQLdb.connect(host="localhost",user="root",password="",database="test")
while True:
    file = pd.read_csv('C:\\Users\\Hp\\Desktop\\as\\LAPTOP-GLP58E12_20220329-000030\\lp.csv')
    lr = file.iloc[-1].tolist()
    print(lr)
    query="UPDATE perfmon set `date`=%s,`pc1`=%s,`pc2`=%s where 1"
    mycursor=mydb.cursor()
    mycursor.execute(query,lr)
    mydb.commit()

