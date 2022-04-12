import MySQLdb
from datetime import datetime

mydb=MySQLdb.connect(host="sql.freedb.tech",user="freedb_perfmonadmin",password="a&DzbGE&9T75Nby",database="freedb_perfmon")
print (mydb)
now = datetime.now()
datetime = now.strftime("%H:%M:%S")
a = mydb.cursor()
a.execute("DROP TABLE Distance")
#a.execute("CREATE TABLE Distance (DateTime VARCHAR(255), Distance VARCHAR(255))")
#sql = ("INSERT INTO Distance(DateTime,Distance) VALUE(%s,%s)") 
#data= (datetime,"23")

#a.execute(sql,data)
#mydb.commit()
