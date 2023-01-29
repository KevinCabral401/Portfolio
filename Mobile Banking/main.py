

#Kevin Cabral and Michael Matthews

from flask import *
from flask_sqlalchemy import *
from flask_login import LoginManager, UserMixin, login_user, logout_user, current_user, login_required

app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///database.db'
db = SQLAlchemy(app)

class User(UserMixin, db.Model):
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(20), unique=True, nullable=False)
    password = db.Column(db.String(40), nullable=False)
    name = db.Column(db.String(40), nullable=False)
    account = db.relationship('Account', backref='user')

class Account(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    user_id = db.Column(db.Integer, db.ForeignKey('user.id'))
    name = db.Column(db.String(20), nullable=False)
    number = db.Column(db.Integer, unique=True, nullable=False)
    amount = db.Column(db.Integer, nullable=False)
    type = db.Column(db.String(15), nullable=False)

app.config['SECRET_KEY'] = 'whatAnAmazingSecurityKey!!'
login_manager = LoginManager(app)
login_manager.init_app(app)

@login_manager.user_loader
def load_user(uid):
    user = User.query.filter_by(id=uid).first()
    return user

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/create', methods=['POST', 'GET'])
def create():
        if request.method == 'POST':
            name = request.form['name']
            username = request.form['username']
            password = request.form['password']
            userTest = User.query.filter_by(username=username).first()
            if userTest == None:
                user = User(name=name, username=username, password=password)
                db.session.add(user)
                db.session.commit()
                login_user(user)
                return redirect('/')
            else:
                failure = True;
                return render_template('create.html', failure=failure)
        else:
            return render_template('create.html')

@app.route('/view_all')
@login_required
def view_all():
    user = load_user(current_user.get_id())
    accounts = Account.query.filter(Account.user_id == user.username).all()
    return render_template('view.html', accounts=accounts)

@app.route('/add', methods=['POST', 'GET'])
@login_required
def add():
    if request.method == 'POST':
        name = request.form['name']
        number = request.form['number']
        amount = request.form['amount']
        type = request.form['type']
        userTest = Account.query.filter_by(number=number).first()
        if userTest == None:
            user = load_user(current_user.get_id())
            account = Account(name=name, number=number, amount=amount, type=type, user_id=user.username)
            db.session.add(account)
            db.session.commit()
            login_user(user)
            return redirect('/view_all')
        else:
            failure = True;
            return render_template('addAcount.html', failure=failure)
    else:
        return render_template('addAcount.html')

@app.route('/update', methods=['POST', 'GET'])
@login_required
def update():
    if request.method =='GET':
        return render_template('update.html')
    elif request.method == 'POST':
        oldPassword = request.form['oldPASS']
        newPassword = request.form['newPASS']
        user = User.query.filter_by(password=oldPassword).first()
        if user != None and oldPassword == user.password:
            user.password = newPassword
            db.session.commit()
            return redirect('/')
        else:
            failure = True;
            return render_template('update.html', failure=failure)

@app.route('/updateAccount/<id>', methods = ['GET','POST'])
@login_required
def updateAccount(id):
    if(request.method == 'GET'):
        entry = Account.query.filter_by(id=id).first()
        return render_template('create.html', entry=entry, id=id)
    elif(request.method == 'POST'):
        entry = Account.query.filter_by(id=id).first()
        name = request.form['name']
        number = request.form['number']
        amount = request.form['amount']
        type = request.form['type']
        entry.name = name
        entry.number = number
        entry.amount = amount
        entry.type = type
        db.session.commit()
        return redirect('/view_all')

@app.route('/delete/<id>', methods=['GET'])
@login_required
def delete(id):
    if (request.method == 'GET'):
        entry = Account.query.filter_by(id=id).first()
        db.session.delete(entry)
        db.session.commit()
        return redirect('/view_all')

@app.route('/login', methods=['GET', 'POST'])
def login():
    failure = True;
    if request.method == 'GET':
        return render_template('login.html')
    elif request.method == 'POST':
        username = request.form['username']
        password = request.form['password']
        user = User.query.filter_by(username=username).first()
        if user != None and password == user.password:
            login_user(user)
            failure = False;
            return redirect('/')
        else:
            return render_template('login.html', failure=failure)

@app.route('/loginAPI/', methods=['POST'])
def loginAPI():
    failure = True;
    if request.method == 'POST':
        username = request.form['username']
        password = request.form['password']
        user = User.query.filter_by(username=username).first()
        if user != None and password == user.password:
            login_user(user)
            return "Success"
        else:
            return "Failed"
@app.route('/logout')
@login_required
def logout():
    logout_user()
    return redirect('/')

@app.errorhandler(401)
def error401(err):
    return render_template('401.html', err=err)

@app.errorhandler(404)
def error401(err):
    return render_template('404.html', err=err)

app.app_context().push()
if __name__ == '__main__':
    app.run(debug=True)
