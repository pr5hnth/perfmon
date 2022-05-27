from signal import *
import os, time
import MySQLdb
mydb=MySQLdb.connect(host="bhnliwdehpi4qgqkza9m-mysql.services.clever-cloud.com",user="uc32cnli5sf72zz5",password="qulRRKw8dPfS9E4SiEsa",database="bhnliwdehpi4qgqkza9m")


def clean():
    print ("clean me")
    query="UPDATE perfmon set `pc1`=1, `pc2`=1, `stat`= 'Offline' where 1"
    mycursor=mydb.cursor()
    mycursor.execute(query)
    mydb.commit()
    os._exit(0)
for sig in (SIGABRT, SIGINT, SIGTERM):
    signal(sig, clean)
time.sleep(10)
