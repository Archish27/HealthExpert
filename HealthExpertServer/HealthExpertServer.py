from flask import Flask, request, jsonify
from flask_mysqldb import MySQL
from werkzeug.utils import secure_filename
import json, os
from pyfcm import FCMNotification
import hashlib
from firebase import firebase
import base64
from os.path import join, dirname, realpath

push_service = FCMNotification(api_key='AIzaSyAJAUIw8JlY64FYmft7MfZyyZqlqd18NII')

app = Flask(__name__)
app.config['MYSQL_HOST'] = 'localhost'
app.config['MYSQL_USER'] = 'root'
app.config['MYSQL_PASSWORD'] = ''
app.config['MYSQL_DB'] = 'hedb'
dir = dirname(realpath(__file__))
app.config['UPLOAD_FOLDER'] = join(dir, "static\\uploads\\")
app.config['UPLOAD_PATH'] = "static/uploads/"
mysql = MySQL(app)
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg', 'PNG', 'JPG', 'JPEG'}

authentication = firebase.FirebaseAuthentication('9xOiL0GKPgXmUXO85nYFCFg0pj6H0vdzgnVYUuwy', 'archishthakkar@gmail.com')
firebase = firebase.FirebaseApplication('https://newfirebaseapplication-7755a.firebaseio.com/', authentication)


# JSONResponses
# Register Status


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/auth/register/doctor', methods=['POST', 'GET'])
def register_doctor():
    if request.method == 'POST':
        name = request.form['name']
        emailid = request.form['emailid'].encode('utf-8')
        accesstoken = hashlib.sha256(emailid).hexdigest()
        regid = request.form['regid']
        speciality = request.form['speciality']
        city = request.form['city']
        gender = request.form['gender']
        pincode = request.form['pincode']
        experience = request.form['experience']
        phoneno = request.form['phoneno']
        password = hashlib.sha256(request.form['password'].encode('utf-8')).hexdigest()
        fuid = request.form['fuid'].encode('utf-8')
        file = request.files['image']
        photo = app.config['UPLOAD_PATH'] + accesstoken + file.filename
        filename = secure_filename(accesstoken + file.filename)

        file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))

        status = 0
        r_id = 2
        cur = mysql.connection.cursor()
        cur.execute(
            '''INSERT INTO doctor(d_name,d_phoneno,d_regid,d_gender,d_speciality,d_experience,d_city,d_pincode,d_accesstoken,d_photo,d_fuid) values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)''',
            (name, phoneno, regid, gender, speciality, experience, city, pincode, accesstoken, photo, fuid))
        cur.execute(
            '''INSERT into users(u_accesstoken,u_emailid,u_password,u_status,r_id) values(%s,%s,%s,%s,%s)''',
            (accesstoken, emailid, password, status, r_id))
        mysql.connection.commit()

        return jsonify(status=True,
                       message="Registered Successfully")


@app.route('/auth/register/patient/icon', methods=['POST', 'GET'])
def patient_register_icon():
    if request.method == 'POST':
        data = request.data
        dataD = json.loads(data)
        name = dataD['name'].encode('utf-8')
        dob = dataD['dob'].encode('utf-8')
        height = dataD['height'].encode('utf-8')
        weight = dataD['weight'].encode('utf-8')
        emailid = dataD['emailid'].encode('utf-8')
        occupation = dataD['occupation'].encode('utf-8')
        symptoms = dataD['symptoms'].encode('utf-8')
        bloodgroup = dataD['bloodgroup'].encode('utf-8')
        history = dataD['history'].encode('utf-8')
        investigations = dataD['investigations'].encode('utf-8')
        mothersname = dataD['mothersname'].encode('utf-8')
        mothersymptom = dataD['mothersymptom'].encode('utf-8')
        fathername = dataD['fathername'].encode('utf-8')
        fathersymptom = dataD['fathersymptom'].encode('utf-8')
        accesstoken = hashlib.sha256(emailid).hexdigest()
        city = dataD['city'].encode('utf-8')
        gender = dataD['gender'].encode('utf-8')
        pincode = dataD['pincode'].encode('utf-8')
        phoneno = dataD['phoneno'].encode('utf-8')
        password = hashlib.sha256(dataD['password'].encode('utf-8')).hexdigest()
        file = request.files['image']
        photo = "/" + accesstoken + file.filename
        f = os.path.join(app.config['UPLOAD_FOLDER'], photo)
        f.save(f)
        status = 1
        r_id = 3
        cur = mysql.connection.cursor()
        cur.execute(
            '''INSERT INTO patient(p_name,p_dob,p_gender,p_height,p_weight,p_accesstoken,p_phoneno,p_occupation,p_symptoms,p_bloodgroup,p_history,p_investigations,p_city,p_pincode,p_mothername,p_mothersymptoms,p_fathername,p_fathersymptoms,p_photo) 
              values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)''',
            (name, dob, gender, height, weight, accesstoken, phoneno, occupation, symptoms, bloodgroup, history,
             investigations, city, pincode, mothersname, mothersymptom, fathername, fathersymptom, photo))
        cur.execute('''INSERT into users(u_accesstoken,u_emailid,u_password,u_status,r_id) values(%s,%s,%s,%s,%s)''',
                    (accesstoken, emailid, password, status, r_id))
        mysql.connection.commit()
        return jsonify(status=True,
                       message="Registered Successfully")


@app.route('/auth/register/patient/noicon', methods=['POST', 'GET'])
def patient_register_noicon():
    if request.method == 'POST':
        data = request.data
        dataD = json.loads(data)
        name = dataD['name'].encode('utf-8')
        dob = dataD['dob'].encode('utf-8')
        height = dataD['height'].encode('utf-8')
        weight = dataD['weight'].encode('utf-8')
        emailid = dataD['emailid'].encode('utf-8')
        occupation = dataD['occupation'].encode('utf-8')
        symptoms = dataD['symptoms'].encode('utf-8')
        bloodgroup = dataD['bloodgroup'].encode('utf-8')
        history = dataD['history'].encode('utf-8')
        investigations = dataD['investigations'].encode('utf-8')
        mothersname = dataD['mothername'].encode('utf-8')
        mothersymptom = dataD['mothersymptoms'].encode('utf-8')
        fathername = dataD['fathername'].encode('utf-8')
        fathersymptom = dataD['fathersymptoms'].encode('utf-8')
        accesstoken = hashlib.sha256(emailid).hexdigest()
        city = dataD['city'].encode('utf-8')
        gender = dataD['gender'].encode('utf-8')
        pincode = dataD['pincode'].encode('utf-8')
        phoneno = dataD['phoneno'].encode('utf-8')
        password = hashlib.sha256(dataD['password'].encode('utf-8')).hexdigest()
        status = 1
        r_id = 3
        cur = mysql.connection.cursor()
        cur.execute(
            '''INSERT INTO patient(p_name,p_dob,p_gender,p_height,p_weight,p_accesstoken,p_phoneno,p_occupation,p_symptoms,p_bloodgroup,p_history,p_investigations,p_city,p_pincode,p_mothername,p_mothersymptoms,p_fathername,p_fathersymptoms) 
              values(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)''',
            (name, dob, gender, height, weight, accesstoken, phoneno, occupation, symptoms, bloodgroup, history,
             investigations, city, pincode, mothersname, mothersymptom, fathername, fathersymptom))
        cur.execute('''INSERT into users(u_accesstoken,u_emailid,u_password,u_status,r_id) values(%s,%s,%s,%s,%s)''',
                    (accesstoken, emailid, password, status, r_id))
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


@app.route('/auth/login/fuid', methods=['GET', 'POST'])
def user_update_fuid():
    if request.method == 'POST':
        data = request.data
        dataD = json.loads(data)
        accesstoken = dataD['accesstoken'].encode('utf-8')
        fuid = dataD['fuid'].encode('utf-8')
        cur = mysql.connection.cursor()
        cur.execute('''UPDATE doctor SET d_fuid=%s WHERE d_accesstoken=%s''',
                    (fuid, accesstoken))
        mysql.connection.commit()
        return jsonify(status=True, message="Login Successful")


@app.route('/doctor/patient/symptoms', methods=['GET', 'POST'])
def get_symptoms():
    if request.method == 'POST':
        cur = mysql.connection.cursor()
        cur.execute('SELECT s_id,s_name FROM symptom ORDER BY s_name ASC')
        list_data = []
        for row in cur:
            dataDict = {'id': row[0], 'sname': row[1]}
            list_data.append(dataDict)
        return jsonify(data=list_data)


@app.route('/doctor/speciality', methods=['GET', 'POST'])
def get_speciality():
    if request.method == 'POST':
        cur = mysql.connection.cursor()
        cur.execute('''SELECT * FROM speciality ORDER BY s_id''')
        list_data = []
        for row in cur:
            dataDict = {'s_id': row[0], 's_name': row[1], 's_description': row[2]}
            list_data.append(dataDict)
        return jsonify(data=list_data)


@app.route('/doctor/mypatients', methods=['GET', 'POST'])
def get_mypatients():
    if request.method == 'POST':
        data = request.data
        dataD = json.loads(data)
        accesstoken = dataD['accessToken'].encode('utf-8')
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


# TODO Patient + PatientDetails
@app.route('/doctor/patients', methods=['GET', 'POST'])
def get_patients():
    if request.method == 'POST':
        cur = mysql.connection.cursor()
        cur.execute('''SELECT * FROM patient ORDER BY p_name ''')
        list_data = []
        for row in cur:
            dataDict = {'pid': row[0],
                        'name': row[1],
                        'dob': row[2],
                        'gender': row[3],
                        'height': row[4],
                        'weight': row[5],
                        'phoneno': row[6],
                        'occupation': row[7],
                        'symptoms': row[8],
                        'bloodgroup': row[9],
                        'history': row[10],
                        'investigations': row[11],
                        'city': row[12],
                        'pincode': row[13],
                        'mothername': row[14],
                        'mothersymptoms': row[15],
                        'fathername': row[16],
                        'fathersymptoms': row[17],
                        'photo': row[18],
                        'accesstoken': row[19]
                        }
            list_data.append(dataDict)

        return jsonify(data=list_data)


@app.route('/admin/doctors', methods=['GET', 'POST'])
def get_doctors():
    if request.method == 'POST':

        cur = mysql.connection.cursor()
        cur.execute(
            '''SELECT DISTINCT d_name,u_emailid,d_phoneno,d_pincode,d_city,s_name,d_gender,d_experience,d_regid,d_accesstoken,d_photo,d_fuid FROM doctor,users,speciality where doctor.d_accesstoken = users.u_accesstoken AND doctor.d_speciality=speciality.s_id  AND users.r_id=2 AND users.u_status=0''')
        list_data = []
        for row in cur:
            dataDict = {'name': row[0],
                        'emailid': row[1],
                        'phoneno': row[2],
                        'pincode': row[3],
                        'city': row[4],
                        'speciality': row[5],
                        'gender': row[6],
                        'experience': row[7],
                        'regid': row[8],
                        'accesstoken': row[9],
                        'photo': row[10],
                        'fuid': row[11]}
            list_data.append(dataDict)

        return jsonify(data=list_data)


@app.route('/admin/doctors/status', methods=['GET', 'POST'])
def status_doctor():
    if request.method == 'POST':
        data = request.data
        dataD = json.loads(data)
        accesstoken = dataD['accesstoken'].encode('utf-8')
        fuid = dataD['firebaseid'].encode('utf-8')
        status = dataD['status']

        cur = mysql.connection.cursor()
        cur.execute(
            '''UPDATE users SET u_status = %s where u_accesstoken = %s''',
            (status, accesstoken))
        mysql.connection.commit()
        if status != 99:
            result = firebase.get('/Users/' + fuid.decode('utf-8'), None)
            registration_id = result.get("device_token")
            name = result.get("name")
            push_service.notify_single_device(registration_id=registration_id, message_title="Health Expert"
                                              , message_body=name + " your registration is verified")
            return jsonify(status=True,
                           message="Doctor Verified")
        else:
            return jsonify(status=True,
                           message="Doctor Rejected")


@app.route('/patient/doctors', methods=['GET', 'POST'])
def get_doctors_for_patients():
    if request.method == 'POST':
        data = request.data
        dataD = json.loads(data)
        sname = dataD['s_name'].encode('utf-8')

        cur = mysql.connection.cursor()
        cur.execute(
            '''SELECT d_name,u_emailid,d_phoneno,d_pincode,d_city,s_name,d_gender,d_experience,d_regid,d_accesstoken,d_photo FROM doctor,users,speciality,rating where doctor.d_accesstoken = users.u_accesstoken AND doctor.d_speciality=speciality.s_id  AND users.r_id=2 AND users.u_status=1 AND speciality.s_name=%s ORDER BY rating.r_score DESC''',
            (sname,))
        list_data = []
        for row in cur:
            cur_rate = mysql.connection.cursor()
            cur_rate.execute(
                '''SELECT COUNT(r_like) as likes,AVG(r_score) as ratings FROM rating where r_daccesstoken=%s''',
                (str(row[9]),))
            for rate_row in cur_rate:
                dataDict = {'name': row[0],
                            'emailid': row[1],
                            'phoneno': row[2],
                            'pincode': row[3],
                            'city': row[4],
                            'speciality': row[5],
                            'gender': row[6],
                            'experience': row[7],
                            'regid': row[8],
                            'accesstoken': row[9],
                            'photo': row[10],
                            'likes': rate_row[0],
                            'ratings': str(rate_row[1])}
                list_data.append(dataDict)

        return jsonify(data=list_data)


@app.route('/doctor/alldoctors', methods=['GET', 'POST'])
def get_doctors_all():
    if request.method == 'POST':

        cur = mysql.connection.cursor()
        cur.execute(
            '''SELECT DISTINCT d_name,u_emailid,d_phoneno,d_pincode,d_city,s_name,d_gender,d_experience,d_regid,d_accesstoken,d_photo,d_fuid FROM doctor,users,speciality,rating where doctor.d_accesstoken = users.u_accesstoken AND doctor.d_speciality=speciality.s_id  AND users.r_id=2 AND users.u_status=1 ORDER BY rating.r_score DESC''')
        list_data = []
        for row in cur:
            cur_rate = mysql.connection.cursor()
            cur_rate.execute(
                '''SELECT COUNT(r_like) as likes,AVG(r_score) as ratings FROM rating where r_daccesstoken=%s''',
                (str(row[9]),))
            for rate_row in cur_rate:
                dataDict = {'name': row[0],
                            'emailid': row[1],
                            'phoneno': row[2],
                            'pincode': row[3],
                            'city': row[4],
                            'speciality': row[5],
                            'gender': row[6],
                            'experience': row[7],
                            'regid': row[8],
                            'accesstoken': row[9],
                            'photo': row[10],
                            'fuid': row[11],
                            'likes': rate_row[0],
                            'ratings': str(rate_row[1])}
                list_data.append(dataDict)

        return jsonify(data=list_data)


@app.route('/bookmark/add', methods=['GET', 'POST'])
def bookmark_operation():
    if request.method == 'POST':
        data = request.data
        dataD = json.loads(data)
        source_token = dataD['source_token'].encode('utf-8')
        destination_token = dataD['destination_token'].encode('utf-8')
        role = dataD['role'].encode('utf-8')
        cur = mysql.connection.cursor()
        cur.execute('''INSERT into bookmarks(source_token,destination_token) VALUES (%s,%s)''',
                    (source_token, destination_token))
        mysql.connection.commit()
        if role == 2:
            return jsonify(status=True, message="Added to My Patients")
        else:
            return jsonify(status=True, message="Added to My Doctors")


# For patients get all my doctors
@app.route('/patient/bookmark/doctors', methods=['GET', 'POST'])
def bookmark_mydoctors():
    if request.method == 'POST':
        data = request.data
        dataD = json.loads(data)
        source_token = dataD['source_token'].encode('utf-8')
        cur = mysql.connection.cursor()
        cur.execute(
            '''SELECT d_name,u_emailid,d_phoneno,d_pincode,d_city,s_name,d_gender,d_experience,d_regid,d_accesstoken,d_photo,d_fuid FROM doctor where d_accesstoken IN (SELECT b_destination from bookmarks WHERE b_source=%s)''',
            (source_token))
        list_data = []
        for row in cur:
            dataDict = {'name': row[0],
                        'emailid': row[1],
                        'phoneno': row[2],
                        'pincode': row[3],
                        'city': row[4],
                        'speciality': row[5],
                        'gender': row[6],
                        'experience': row[7],
                        'regid': row[8],
                        'accesstoken': row[9],
                        'photo': row[10],
                        'fuid': row[11]}
            list_data.append(dataDict)

        return jsonify(data=list_data)


# For doctors get all my patients
@app.route('/doctor/bookmark/patients', methods=['GET', 'POST'])
def bookmark_mypatients():
    if request.method == 'POST':
        data = request.data
        dataD = json.loads(data)
        source_token = dataD['source_token'].encode('utf-8')
        cur = mysql.connection.cursor()
        cur.execute(
            '''SELECT * FROM patient where p_accesstoken IN (SELECT b_destination from bookmarks WHERE b_source=%s)''',
            (source_token))
        list_data = []
        for row in cur:
            dataDict = {'pid': row[0],
                        'name': row[1],
                        'dob': row[2],
                        'gender': row[3],
                        'height': row[4],
                        'weight': row[5],
                        'phoneno': row[6],
                        'occupation': row[7],
                        'symptoms': row[8],
                        'bloodgroup': row[9],
                        'history': row[10],
                        'investigations': row[11],
                        'city': row[12],
                        'pincode': row[13],
                        'mothername': row[14],
                        'mothersymptoms': row[15],
                        'fathername': row[16],
                        'fathersymptoms': row[17],
                        'photo': row[18],
                        'accesstoken': row[19]
                        }
            list_data.append(dataDict)

        return jsonify(data=list_data)


@app.route('/messaging/notify', methods=['GET', 'POST'])
def messaging_notify():
    if request.method == 'POST':
        data = request.data
        dataD = json.loads(data)
        source_fuid = dataD['source_fuid']
        destination_fuid = dataD['destination_fuid']
        source_result = firebase.get('/Users/' + source_fuid, None)

        result = firebase.get('/Users/' + destination_fuid, None)
        registration_id = result.get("device_token")
        message_title = source_result.get("name")
        message_body = "1 new message"
        action = "com.healthexpert.doctorchat_TARGET_NOTIFICATION"
        data_message = {'from_did': source_fuid}
        result_push = push_service.notify_single_device(registration_id=registration_id, message_title=message_title,
                                                        message_body=message_body, click_action=action,
                                                        data_message=data_message)
        return jsonify(result_push)


def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS


if __name__ == '__main__':
    app.run(host='192.168.0.102', port=5000)
