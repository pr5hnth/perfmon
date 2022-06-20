from email.mime import image
from tkinter import *
import pandas as pd
import threading
import MySQLdb
import atexit
from signal import *
import os, time

mydb=MySQLdb.connect(host="bhnliwdehpi4qgqkza9m-mysql.services.clever-cloud.com",user="uc32cnli5sf72zz5",password="qulRRKw8dPfS9E4SiEsa",database="bhnliwdehpi4qgqkza9m")

cmd=""
root = Tk()
root.title('Start Server PerfMon')
root.iconbitmap('img/ico.ico')
root.geometry('500x300')


global is_on
is_on = False


my_label = Label(root, text = 'Server Monitoring is Off',fg='grey',font=("Google Sans",18))
my_label.pack(pady=20)


def startperfmon():
    while True:
        file = pd.read_csv('C:\\Users\\Hp\\Desktop\\as\\LAPTOP-GLP58E12_20220618-000036\\lp.csv')
        lr = file.iloc[-1].tolist()
        query="UPDATE perfmon set `date`=%s,`pc1`=TRUNCATE(%s,2),`pc2`=TRUNCATE(%s,2),`stat`= 'Online' where id=1"
        mycursor=mydb.cursor()
        mycursor.execute(query,lr)
        mydb.commit()
        cmd()
        if not is_on:
            query="UPDATE perfmon set `pc1`=0, `pc2`=0, `stat`= 'Offline' where id=1"
            mycursor=mydb.cursor()
            mycursor.execute(query)
            mydb.commit()
            break

def switch():
    global is_on
    if is_on:
        on_btn.config(image=off)
        my_label.config(text="Server Monitoring is Off", fg="grey")
        is_on = False
        root.destroy()
        th.join()
        
      

    else:
        on_btn.config(image=on)
        my_label.config(text="Server Monitoring is On", fg="green")
        is_on = True
        th.start()
            
        

on = PhotoImage(file="img/on.png")
off = PhotoImage(file="img/of.png")

on_btn = Button(root, image = off , bd=0, command=switch)
on_btn.pack(pady=50)


def cmd():
    query="SELECT cmd FROM perfmon WHERE 1"
    mycursor=mydb.cursor()
    mycursor.execute(query)
    myresult = mycursor.fetchall()
    for x in myresult:
      for y in x:
         cmd = y;
         if(cmd=="RS"):
             os.system("shutdown /r /t ")
             print("Rematch Motherfucker")
    mydb.commit()
    

    
def doSomething(*args):
    switch()
    query="UPDATE perfmon set `pc1`=0, `pc2`=0, `stat`= 'Offline' where id=1"
    mycursor=mydb.cursor()
    mycursor.execute(query)
    mydb.commit()
    os._exit(0)

th = threading.Thread(target=startperfmon)
root.protocol('WM_DELETE_WINDOW', doSomething)

for sig in (SIGABRT, SIGINT, SIGTERM):
    signal(sig, doSomething)
root.mainloop()
