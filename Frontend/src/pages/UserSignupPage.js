import React from 'react';
import { Component } from 'react';

export class UserSignupPage extends React.Component {

    state = {
        displayName: '',
        userName: '',
        password: '',
        confirmPassword: '',
        pendingApiCall: false
    }

    onChangeDisplayName = (event) => {
        this.setState({ displayName: event.target.value })
    }

    onChangeUserName = (event) => {
        this.setState({ userName: event.target.value })
    }

    onChangePassword = (event) => {
        this.setState({ password: event.target.value })
    }
    onClickSignup = () => {
        const user = {
            userName: this.state.userName,
            displayName: this.state.displayName,
            password: this.state.password
        };
        this.setState({ pendingApiCall: true })
        this.props.actions.postSignup(user);

    }

    render() {
        return (<div className="container">
            <h1 className="text-center">Sign Up</h1>
            <div className="col-12  mb-3">
                <label>Display Name</label>
                <input className='form-control' placeholder="Your display Name" value={this.state.displayName} onChange={this.onChangeDisplayName} />
            </div>
            <div className="col-12  mb-3">
                <label> User Name</label>
                <input className='form-control' placeholder="Your display UserName" value={this.state.userName} onChange={this.onChangeUserName} />
            </div>
            <div className='col-12 mb-3'>
                <label> Password</label>
                <input className='form-control' type="password" value={this.state.password} placeholder="Your display Password" onChange={this.onChangePassword} />
            </div>
            <div>
                <label>Confirm Password</label>
                <input className='form-control' type='password' placeholder="Your display Confirm Password" />
            </div>
            <div className='text-center'>
                <button className="btn btn-primary" type='submit' onClick={this.onClickSignup} disabled={this.state.pendingApiCall} ><div class="spinner-border" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>Sign Up</button>
            </div>
        </div>);
    }
}

UserSignupPage.defaultProps = {
    actions: {
        postSignup: () => new Promise((resolve, reject) => {
            resolve({});
        })
    }
}
export default UserSignupPage;