from tkinter import *
import MySQLdb
from email.mime import image
import pandas as pd
import threading

mydb=MySQLdb.connect(host="bhnliwdehpi4qgqkza9m-mysql.services.clever-cloud.com",user="uc32cnli5sf72zz5",password="qulRRKw8dPfS9E4SiEsa",database="bhnliwdehpi4qgqkza9m")
global is_on
is_on = False
root = Tk()

class MainWindow(object):

  def __init__(self):
    print("initiated ..")
    root = Tk()
    root.title('Start Server PerfMon')
    root.iconbitmap('img/ico.ico')
    root.geometry('500x300')
 

    

  def __enter__(self):
    def startperfmon():
      while True:
        file = pd.read_csv('C:\\Users\\Hp\\Desktop\\as\\LAPTOP-GLP58E12_20220418-000034\\lp.csv')
        lr = file.iloc[-1].tolist()
        query="UPDATE perfmon set `date`=%s,`pc1`=TRUNCATE(%s,2),`pc2`=TRUNCATE(%s,2),`stat`= 'Online' where 1"
        mycursor=mydb.cursor()
        mycursor.execute(query,lr)
        mydb.commit()
        if not is_on:
            query="UPDATE perfmon set `pc1`=0, `pc2`=0, `stat`= 'Offline' where 1"
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
      
        

      else:
        on_btn.config(image=on)
        my_label.config(text="Server Monitoring is On", fg="green")
        is_on = True
        th = threading.Thread(target=startperfmon)
        th.start()
            
        


    on = PhotoImage(file="img/on.png")
    off = PhotoImage(file="img/of.png")
    on_btn = Button(root, image = off , bd=0, command=switch)
    on_btn.pack(pady=50)
    return self

  def __exit__(self, exc_type, exc_val, exc_tb):
    print("Main Window is closing, call any function you'd like here!")
    query="UPDATE perfmon set `pc1`=0, `pc2`=0, `stat`= 'Offline' where 1"
    mycursor=mydb.cursor()
    mycursor.execute(query)
    mydb.commit()



with MainWindow() as w:
  w.root.mainloop()

print("end of script ..")
