# Assignment No. 5 – Net Centric Programming
# Log-File-Analysis-and-Spam-IP-Detection-using-Java-InetAddress-
# Objective
This is my project where i used java to see the net centric programming in networks and to identify the SPAM IP's using java.net.InetAddress library.
The objective of this assignment is to analyze multiple web server log files, extract IP addresses, count their access frequency, identify spam IPs using DNS blacklist checking, and store results in a file.

# Project Description

This project performs the following tasks:

# 1. Read Log Files

The program reads all `.log` files from the `logs` folder:

* java_access.log
* python_access.log
* cpp_access.log

# 2. Extract IP Addresses

Each log line contains an IP address and a request.
The program extracts IP addresses using regular expressions.

# 3. Ignore Local IPs

Local/private IP addresses are skipped, such as:

* 127.0.0.1
* 192.168.x.x
* 10.x.x.x
* 172.16–172.31.x.x

This is done using `InetAddress` methods.

# 4. Spam IP Detection

Public IPs are checked using the **Spamhaus Blackhole DNS system**:
If an IP is found in blacklist → it is marked as spam.
Spam IPs are stored in:

spam_ips.txt

# 5. Count IP Access

The program counts how many times each IP appears in all log files.

# 6. Sort IPs

All IP addresses are displayed in **descending order** based on access frequency.

## 🧠 Technologies Used

* Java
* File Handling (`FileReader`, `BufferedReader`)
* Collections (`HashMap`, `HashSet`)
* Regular Expressions (Regex)
* InetAddress
* DNS Lookup (Spam Check)

# Project Structure

│
├── List_of_IP.java
├── logs/
│     ├── java_access.log
│     ├── python_access.log
│     ├── cpp_access.log
│
├── spam_ips.txt
└── README.md

# How to Run

1. Create a folder named `logs`
2. Add all `.log` files inside it
3. Compile program:

javac  List_of_IP.java


4. Run program:

java  List_of_IP



# 📊 Output

The program displays:

* IP addresses with access counts (descending order)
* List of spam IPs saved in `spam_ips.txt`


# 📌 Conclusion

This project demonstrates how network logs can be analyzed using Java. It helps understand real-world concepts like IP tracking, spam detection, and DNS-based blacklist checking.

