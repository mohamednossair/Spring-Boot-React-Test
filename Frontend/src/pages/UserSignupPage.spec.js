import React from "react";
import { fireEvent, render, screen } from "@testing-library/react";

import "@testing-library/jest-dom/extend-expect";
import { UserSignupPage } from "./UserSignupPage";

describe("UserSignupPage", () => {
  describe("Layout", () => {
    it("has header of Sign Up", () => {
      const { container } = render(<UserSignupPage />);
      const header = container.querySelector("h1");
      expect(header).toHaveTextContent("Sign Up");
    });

    it("has input for display name", () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const displayNameInput = queryByPlaceholderText("Your display Name");
      expect(displayNameInput).toBeInTheDocument();
    });

    it("has input for User name", () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const displayNameInput = queryByPlaceholderText("Your display UserName");
      expect(displayNameInput).toBeInTheDocument();
    });

    it("has input for Password", () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const displayNameInput = queryByPlaceholderText("Your display Password");
      expect(displayNameInput).toBeInTheDocument();
    });

    it("has input for Password type password", () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const passwordInput = queryByPlaceholderText("Your display Password");
      expect(passwordInput.type).toBe("password");
    });
    it("has input for Confirm Password", () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const displayNameInput = queryByPlaceholderText(
        "Your display Confirm Password"
      );
      expect(displayNameInput).toBeInTheDocument();
    });

    it("has input for Confirm Password type password", () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const confirmPasswordInput = queryByPlaceholderText(
        "Your display Confirm Password"
      );
      expect(confirmPasswordInput.type).toBe("password");
    });

    it("has submit button", () => {
      const { container } = render(<UserSignupPage />);
      const button = container.querySelector("button");
      expect(button).toBeInTheDocument();
    });
  });

  const changeEvent = (content) => {
    return {
      target: {
        value: content,
      },
    };
  };

  const mockAsyncDelayes = () => {
    return jest.fn().mockImplementation(() => {
      return new Promise((resolve, reject) => {
        setTimeout(() => {
          resolve([]);
        }, 300);
      });
    });
  };
  describe("Interactions", () => {
    it("sets the displayName Value into state", () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const displayNameInput = queryByPlaceholderText("Your display Name");

      fireEvent.change(displayNameInput, changeEvent("my-display-name"));
      expect(displayNameInput).toHaveValue("my-display-name");
    });

    it("sets the UserName Value into state", () => {
      const { queryByPlaceholderText } = render(<UserSignupPage />);
      const displayUserName = queryByPlaceholderText("Your display UserName");
      fireEvent.change(displayUserName, changeEvent("my-display-userName"));
      expect(displayUserName).toHaveValue("my-display-userName");
    });

    let button,
      displayNameInput,
      userNameInput,
      passwordInput,
      confirmPasswordInput;
    const setupForSubmit = (props) => {
      const rendered = render(<UserSignupPage {...props} />);

      const { container, queryByPlaceholderText } = rendered;

      displayNameInput = queryByPlaceholderText("Your display Name");
      userNameInput = queryByPlaceholderText("Your display UserName");
      passwordInput = queryByPlaceholderText("Your display Password");
      confirmPasswordInput = queryByPlaceholderText(
        "Your display Confirm Password"
      );

      fireEvent.change(displayNameInput, changeEvent("my-display-Name"));
      fireEvent.change(userNameInput, changeEvent("my-userName"));
      fireEvent.change(passwordInput, changeEvent("my-Password"));
      fireEvent.change(confirmPasswordInput, changeEvent("my-Password"));

      button = container.querySelector("button");
      return rendered;
    };

    it("calls postSignup when the fields are valid and the actions are provided in props", () => {
      const actions = {
        postSignup: jest.fn().mockResolvedValueOnce({}),
      };
      setupForSubmit({ actions });
      fireEvent.click(button);
      expect(actions.postSignup).toHaveBeenCalledTimes(1);
    });

    it("does not throw exception when the fields are valid and the actions are provided in props", () => {
      setupForSubmit();
      expect(() => fireEvent.click(button)).not.toThrow();
    });

    it("calls Post with user Body when the fields are valid and the actions are provided in props", () => {
      const actions = {
        postSignup: jest.fn().mockResolvedValueOnce({}),
      };
      setupForSubmit({ actions });
      fireEvent.click(button);
      const expectedUserObject = {
        userName: "my-userName",
        displayName: "my-display-Name",
        password: "my-Password",
      };
      expect(actions.postSignup).toHaveBeenCalledWith(expectedUserObject);
    });

    it("does not allow user to click the signup button when there is an ongoing api calls", () => {
      const actions = {
        postSignup: mockAsyncDelayes(),
      };
      setupForSubmit({ actions });
      fireEvent.click(button);
      fireEvent.click(button);
      expect(actions.postSignup).toHaveBeenCalledTimes(1);
    });

    it("display spinner when click the signup button", () => {
      const actions = {
        postSignup: mockAsyncDelayes(),
      };
      const { queryByText } = setupForSubmit({ actions });
      fireEvent.click(button);
      const spinner = queryByText("Loading...");
      expect(spinner).toBeInTheDocument();
    });

    it("display validation error for displayName  when error is recived for the field ", async () => {
      const actions = {
        postSignup: jest.fn().mockRejectedValue({
          response: {
            data: { validationErrors: { displayName: "Cannot be null" } },
          },
        }),
      };

      const { queryByText } = setupForSubmit({ actions });
      fireEvent.click(button);
      const errorMessage = await screen.findByText("Cannot be null");
      expect(errorMessage).toBeInTheDocument();
    });

    it("Submit button is not desabiled if Password and confirmPassword are the same", () => {
      setupForSubmit();
      expect(button).not.toBeDisabled();
    });
  });
});
