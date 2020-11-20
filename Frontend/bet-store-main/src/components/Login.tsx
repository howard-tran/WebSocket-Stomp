import axios from 'axios';
import React from 'react';
import { Link } from 'react-router-dom';
import "../styles/Login.css"

let regexpTel: RegExp = /(09|01[2|6|8|9])+([0-9]{8})\b/;

interface AccountEntity {
    name: string,
    surname: string,
    username: string,
    password: string,
    tel: string
}

const initAccount: AccountEntity = {
    name: '',
    surname: '',
    username: '',
    password: '',
    tel: ''
}

interface AccountAction {
    type: string,
    value: string
}


const Accountreducer: React.Reducer<AccountEntity, AccountAction> = (state, action) => {
    switch (action.type) {
        case "name":
            return { ...state, name: action.value }
        case "surname":
            return { ...state, surname: action.value }
        case "username":
            return { ...state, username: action.value }
        case "tel":
            return { ...state, tel: action.value }
        case "password":
            return { ...state, password: action.value }
    }
    return { ...state }
}



const Login: React.FC = () => {

    var [isLogin, changeisLogin] = React.useState<boolean>(true);
    var [isInfoWrong, setisInfoWrong] = React.useState<boolean>(false);
    var [NotiSignup, setNotiSignup] = React.useState<string[]>([]);
    const transForm = (stateSetter: React.Dispatch<boolean>) => {
        stateSetter(!isLogin);
    }

    var repassword = React.createRef<HTMLInputElement>();

    var [account, setaccount] = React.useReducer<React.Reducer<AccountEntity, AccountAction>>(
        Accountreducer,
        initAccount
    )

    const HandleSignupSubmit = (evt: { preventDefault: () => void; }) => {
        evt.preventDefault();
        setNotiSignup([]);
        var msgError: string[] = [];
       
        if (!regexpTel.test(account.tel)) {
            msgError.push("Định dạng sđt không hợp lệ!!!");
        }

        if (repassword.current?.value != account.password) {
            msgError.push("Password chưa trùng. Nhập lại!!!");
        }
        if(msgError.length !== 0 ){
            setNotiSignup(msgError);
            console.log(NotiSignup);
            return;
        }

        axios.post(`/go/api/account/signup`, account)
            .then(
                res => {
                    if (res.data["status"] == 200) {
                        alert("Tài khoản được tạo thành công!!!");
                        changeisLogin(true);
                    }
                    else {
                        setNotiSignup(["Username đã tồn tại trên hệ thống"]);
                    }
                    console.log(res);
                    console.log(res.data);
                }
            ).catch(
                err => {
                    console.log(err);
                }
            );
    }
    const HandleLoginSubmit = (evt: { preventDefault: () => void }) => {
        evt.preventDefault();
        axios.post(`/go/api/account/login`, account)
            .then(
                res => {
                    if (res.data["status"] == 200) {
                        console.log(res.data.data["token"]);
                        window.localStorage.setItem("jwt_token", res.data["data"]["token"]);
                        setisInfoWrong(false);
                        window.location.href = "/";
                    } else {
                        setisInfoWrong(true);
                    }
                }
            ).catch(
                err => {
                    console.log(err);
                    setisInfoWrong(true);
                }
            );
    }

    return (
        <div>
            {isLogin === true && <div className="Login">
                <div className="modal-dialog modal-login">
                    <div className="modal-content">
                        <Link to="/">
                            <h4 className="modal-title">BET Store</h4>
                        </Link>
                        <div className="modal-body">
                            <form onSubmit={HandleLoginSubmit}>
                                <div className="form-group">
                                    <div className="input-group">
                                        <span className="input-group-addon"><i className="fa fa-user" /></span>
                                        <input type="text" className="form-control" value={account.username} onChange={(evt) => (setaccount({ type: "username", value: evt.target.value.toString() }))} name="username" placeholder="Username" required />
                                    </div>
                                </div>
                                <div className="form-group">
                                    <div className="input-group">
                                        <span className="input-group-addon"><i className="fa fa-lock" /></span>
                                        <input type="password" className="form-control" name="password" placeholder="Password" value={account.password} onChange={(evt) => (setaccount({ type: "password", value: evt.target.value.toString() }))} required />
                                    </div>
                                </div>
                                {isInfoWrong === true && <div className="wrong-text-div"><p className="wrong-text">Thông tin đăng nhập chưa chính xác</p></div>}
                                <div className="form-group">
                                    <button type="submit" className="btn btn-primary btn-block btn-lg btn-login">Đăng nhập</button>
                                </div>
                                <p className="hint-text"><a href="#">Quên mật khẩu?</a></p>
                            </form>
                        </div>

                        <div className="modal-footer">
                            <button onClick={() => transForm(changeisLogin)} className="btn btn-primary btn-block btn-ref-signup">Tạo tài khoản mới</button>
                        </div>
                    </div>
                </div>
            </div>}


            {isLogin === false && <div className="Signup">
                <div className="modal-dialog modal-login">

                    <div className="modal-content">
                        <div className="modal-header">
                            <h4 className="modal-title">Đăng kí tài khoản <Link to="/"><h4 className="modal-title">BET Store</h4></Link></h4>
                        </div>
                        <div className="modal-body">
                            <form onSubmit={HandleSignupSubmit}>
                                <div className="row form-group ">
                                    <div className="col input-group ">
                                        <span className="input-group-addon"><i className="fa fa-user" /></span>
                                        <input type="text" className="form-control" name="surname" placeholder="Họ" value={account.surname} onChange={(evt) => (setaccount({ type: "surname", value: evt.target.value.toString() }))} required />
                                    </div>
                                    <div className="col input-group">
                                        <span className="input-group-addon"><i className="fa fa-user" /></span>
                                        <input type="text" className="form-control" name="name" placeholder="Tên" value={account.name} onChange={(evt) => (setaccount({ type: "name", value: evt.target.value.toString() }))} required />
                                    </div>
                                </div>

                                <div className="form-group">
                                    <div className="input-group">
                                        <span className="input-group-addon"><i className="fa fa-user" /></span>
                                        <input type="text" className="form-control" name="username" placeholder="Tên người dùng" value={account.username} onChange={(evt) => (setaccount({ type: "username", value: evt.target.value.toString() }))} required />
                                    </div>
                                </div>

                                <div className="form-group">
                                    <div className="input-group">
                                        <span className="input-group-addon"><i className="fa fa-user" /></span>
                                        <input type="tel" className="form-control" name="tel" placeholder="Số điện thoại" value={account.tel} onChange={(evt) => (setaccount({ type: "tel", value: evt.target.value.toString() }))} required />
                                    </div>
                                </div>
                                <div className="form-group">
                                    <div className="input-group">
                                        <span className="input-group-addon"><i className="fa fa-lock" /></span>
                                        <input type="password" className="form-control" name="password" placeholder="Mật khẩu" value={account.password} onChange={(evt) => (setaccount({ type: "password", value: evt.target.value.toString() }))} required />
                                    </div>
                                </div>
                                <div className="form-group">
                                    <div className="input-group">
                                        <span className="input-group-addon"><i className="fa fa-lock" /></span>
                                        <input type="password" ref={repassword} className="form-control" name="repassword" placeholder="Xác nhận mật khẩu" required />
                                    </div>
                                </div>
                                <div className="wrong-text-div">
                                    {
                                        NotiSignup.map(
                                            i => {
                                                return <p className="wrong-text">{i}</p>
                                            }
                                        )
                                    }
                                </div>
                                <div className="form-group">
                                    <button type="submit" className="btn btn-primary btn-block btn-lg btn-signup">
                                        Đăng kí
                                    </button>
                                    <p className="p-backlogin" >Đã có tài khoản <a href="#" onClick={() => transForm(changeisLogin)}>đăng nhập ngay</a></p>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>}
        </div>
    );
}

export default Login;