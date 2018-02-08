from flask import Flask, request, jsonify
from flask_mysqldb import MySQL
import json
import hashlib

app = Flask(__name__)
app.config['MYSQL_HOST'] = 'localhost'
app.config['MYSQL_USER'] = 'root'
app.config['MYSQL_PASSWORD'] = ''
app.config['MYSQL_DB'] = 'hedb'
mysql = MySQL(app)


# JSONResponses
# Register Status


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/auth/register', methods=['POST', 'GET'])
def user_register():
    if request.method == 'POST':
        data = request.data
        dataD = json.loads(data)
        name = dataD['name'].encode('utf-8')
        emailid = dataD['emailid'].encode('utf-8')
        accesstoken = hashlib.sha256(emailid).hexdigest()
        speciality = dataD['speciality'].encode('utf-8')
        city = dataD['city'].encode('utf-8')
        pincode = dataD['pincode'].encode('utf-8')
        phoneno = dataD['phoneno'].encode('utf-8')
        password = hashlib.sha256(dataD['password'].encode('utf-8')).hexdigest()
        status = 0
        r_id = 3
        cur = mysql.connection.cursor()
        cur.execute(
            '''INSERT INTO users(u_name,u_emailid,u_accesstoken,u_speciality,u_city,u_pincode,u_phoneno,u_password,u_status,r_id) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)''',
            (name, emailid, accesstoken, speciality, city, pincode, phoneno, password, status, r_id))
        mysql.connection.commit()
        return jsonify(status=True,
                       message="Registered Successfully")


@app.route('/auth/login', methods=['GET', 'POST'])
def user_login():
    if request.method == 'POST':
        data = request.data
        dataD = json.loads(data)
        emailid = dataD['emailid'].encode('utf-8')
        password = hashlib.sha256(dataD['password'].encode('utf-8')).hexdigest()
        cur = mysql.connection.cursor()
        cur.execute(
            '''SELECT u_status,u_accesstoken,r_id FROM users where u_emailid = %s AND u_password= %s LIMIT 1''',
            (emailid, password))
        if cur.rowcount == 0:
            return jsonify(status=False,
                           message="Invalid EmailId / Password")
        else:
            for row in cur:
                if row[0] == 0:
                    return jsonify(status=False,
                                   message="Registration hasn't verified!")
                else:
                    return jsonify(status=True,
                                   message="Login Successful",
                                   accessToken=row[1],
                                   role=row[2])


@app.route('/doctor/patients', methods=['GET', 'POST'])
def get_patients():
    if request.method == 'POST':
        data = request.data
        dataD = json.loads(data)
        accesstoken = dataD['accesstoken'].encode('utf-8')
        cur = mysql.connection.cursor()
        cur.execute('''SELECT * FROM patient where d_accesstoken=%s''', (accesstoken))
        list_data = []
        for row in cur:
            dataDict = {'pid': row[0],
                        'name': row[1],
                        'dob': row[2],
                        'gender': row[3],
                        'height': row[4],
                        'weight': row[5],
                        'emailid': row[6],
                        'phoneno': row[7],
                        'occupation': row[8],
                        'symptoms': row[9],
                        'history': row[10],
                        'investigations': row[11],
                        'city': row[12],
                        'pincode': row[13],
                        'mothername': row[14],
                        'mothersymptoms': row[15],
                        'fathername': row[16],
                        'fathersymptoms': row[17],
                        'photo': row[18]
                        }
            list_data.append(dataDict)

        return jsonify(data=list_data)


@app.route('/admin/doctors', methods=['GET', 'POST'])
def get_doctors():
    if request.method == 'POST':

        cur = mysql.connection.cursor()
        cur.execute(
            '''SELECT u_name,u_emailid,u_phoneno,u_pincode,u_city,u_speciality FROM users where u_status=0 AND r_id=3 ''')
        list_data = []
        for row in cur:
            dataDict = {'name': row[0],
                        'emailid': row[1],
                        'phoneno': row[2],
                        'pincode': row[3],
                        'city': row[4],
                        'speciality': row[5]}
            list_data.append(dataDict)

        return jsonify(data=list_data)


if __name__ == '__main__':
    app.run(host='192.168.0.103', port=5000)
