import React from "react";
import Input from "../components/input";
export class UserSignupPage extends React.Component {
  state = {
    displayName: "",
    userName: "",
    password: "",
    confirmPassword: "",
    pendingApiCall: false,
    errors: {},
  };

  onChangeDisplayName = (event) => {
    this.setState({ displayName: event.target.value });
  };

  onChangeUserName = (event) => {
    this.setState({ userName: event.target.value });
  };

  onChangePassword = (event) => {
    this.setState({ password: event.target.value });
  };
  onClickSignup = () => {
    const user = {
      userName: this.state.userName,
      displayName: this.state.displayName,
      password: this.state.password,
    };
    this.setState({ pendingApiCall: true });
    this.props.actions
      .postSignup(user)
      .then((response) => {
        this.setState({ pendingApiCall: false });
      })
      .catch((apiError) => {
        let errors = { ...this.state.errors };
        if (apiError.response.data && apiError.response.data.validationErrors) {
          errors = { ...apiError.response.data.validationErrors };
        }
        this.setState({ pendingApiCall: false, errors });
      });
  };

  render() {
    return (
      <div className="container">
        <h1 className="text-center">Sign Up</h1>
        <div className="col-12  mb-3">
          <Input
            label="Display Name"
            placeholder="Your display Name"
            value={this.state.displayName}
            onChange={this.onChangeDisplayName}
            hasError={this.state.errors.displayName && true}
            error={this.state.errors.displayName}
          />
        </div>
        <div className="col-12  mb-3">
          <Input
            label="User Name"
            placeholder="Your display UserName"
            value={this.state.userName}
            onChange={this.onChangeUserName}
            hasError={this.state.errors.userName && true}
            error={this.state.errors.userName}
          />
        </div>
        <div className="col-12 mb-3">
          <Input
            label="Password"
            type="password"
            value={this.state.password}
            placeholder="Your display Password"
            onChange={this.onChangePassword}
            hasError={this.state.errors.password && true}
            error={this.state.errors.password}
          />
        </div>
        <div>
          <label></label>
          <Input
            label="Confirm Password"
            type="password"
            placeholder="Your display Confirm Password"
            value={this.state.confirmPassword}
            hasError={this.state.errors.confirmPassword && true}
            error={this.state.errors.confirmPassword}
          />
        </div>
        <div className="text-center">
          <button
            className="btn btn-primary"
            type="submit"
            onClick={this.onClickSignup}
            disabled={this.state.pendingApiCall}
          >
            <div className="spinner-border" role="status">
              <span className="visually-hidden">Loading...</span>
            </div>
            Sign Up
          </button>
        </div>
      </div>
    );
  }
}

UserSignupPage.defaultProps = {
  actions: {
    postSignup: () =>
      new Promise((resolve, reject) => {
        resolve({});
      }),
  },
};
export default UserSignupPage;
