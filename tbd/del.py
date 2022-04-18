from email.mime import image
from tkinter import *
import pandas as pd
import threading
import MySQLdb
import atexit

mydb=MySQLdb.connect(host="bhnliwdehpi4qgqkza9m-mysql.services.clever-cloud.com",user="uc32cnli5sf72zz5",password="qulRRKw8dPfS9E4SiEsa",database="bhnliwdehpi4qgqkza9m")
global is_on
is_on = False
class MainWindow(object):

  def __init__(self):
    root = Tk()
    root.title('Start Server PerfMon')
    root.iconbitmap('img/ico.ico')
    root.geometry('500x300')
    my_label = Label(root, text = 'Server Monitoring is Off',fg='grey',font=("Google Sans",18))
    my_label.pack(pady=20)
    on = PhotoImage(file="img/on.png")
    off = PhotoImage(file="img/of.png")
    on_btn = Button(root, image = off , bd=0, command=switch)
    on_btn.pack(pady=50)

  
  def __exit__(self, exc_type, exc_val, exc_tb):
    query="UPDATE perfmon set `pc1`=0, `pc2`=0, `stat`= 'Offline' where 1"
    mycursor=mydb.cursor()
    mycursor.execute(query)
    mydb.commit()
with MainWindow() as w:
  w.root.mainloop()

print("end of script ..")
