import React from "react";
import { fireEvent, render, screen } from "@testing-library/react";

import Input from "./input.js";

describe("Layout", () => {
  it("has input item", () => {
    const { container } = render(<Input />);
    const input = container.querySelector("input");
    expect(input).toBeInTheDocument();
  });

  it("displays the lable provided in the props", () => {
    const { queryByText } = render(<Input label="Test Label" />);
    const label = queryByText("Test Label");
    expect(label).toBeInTheDocument();
  });

  it("dosenot display lable if not provided in the props", () => {
    const { container } = render(<Input />);
    const label = container.querySelector("label");
    expect(label).not.toBeInTheDocument();
  });

  it("has text type when Type isnot provided in the props", () => {
    const { container } = render(<Input />);
    const text = container.querySelector("input");
    expect(text.type).toBe("text");
  });
  it("has text type Password when Type password provided in the props", () => {
    const { container } = render(<Input type="password" />);
    const text = container.querySelector("input");
    expect(text.type).toBe("password");
  });

  it("display placeholder when place holder is provided in the props", () => {
    const { container } = render(<Input placeholder="Test PlaceHolder" />);
    const input = container.querySelector("input");
    expect(input.placeholder).toBe("Test PlaceHolder");
  });

  it("display Value when place holder is provided in the props", () => {
    const { container } = render(<Input value="Test Value" />);
    const input = container.querySelector("input");
    expect(input.value).toBe("Test Value");
  });

  it("has onChange callback when it provided in the props", () => {
    const onChange = jest.fn();
    const { container } = render(<Input onChange={onChange} />);
    const input = container.querySelector("input");
    fireEvent.change(input, { target: { value: "new Input" } });
    expect(onChange).toHaveBeenCalledTimes(1);
  });

  it("has Default style when there is no validation notification success or error", () => {
    const { container } = render(<Input />);
    const input = container.querySelector("input");
    expect(input.className).toBe("form-control");
  });

  it("has Success Style when has Error property is false", () => {
    const { container } = render(<Input hasError={false} />);

    const input = container.querySelector("input");

    expect(input.className).toBe("form-control is-valid");
  });
  it("has Error Style when has Error property is true", () => {
    const { container } = render(<Input hasError={true} />);

    const input = container.querySelector("input");

    expect(input.className).toBe("form-control is-invalid");
  });
  it("display error Message when hasError is true and it provided", () => {
    const { queryByText } = render(
      <Input hasError={true} error="Cannot be null" />
    );
    expect(queryByText("Cannot be null")).toBeInTheDocument();
  });

  it("don't display error Message when hasError isnot provided", () => {
    const { queryByText } = render(<Input error="Cannot be null" />);
    expect(queryByText("Cannot be null")).not.toBeInTheDocument();
  });
});
