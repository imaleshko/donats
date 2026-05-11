import { useLogin } from "@/pages/Auth/Login/useLogin.ts";
import { type ChangeEvent, type SubmitEvent, useState } from "react";
import { Link } from "react-router";
import styles from "@/pages/Auth/Auth.module.css";

const Login = () => {
  const [data, setData] = useState({
    email: "",
    password: "",
  });

  const { login, isPending, isError, error } = useLogin();

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = (e: SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();

    login({
      email: data.email.trim(),
      password: data.password,
    });
  };

  return (
    <div className={styles.wrapper}>
      <form className={styles.form} onSubmit={handleSubmit}>
        <h2 className={styles.formTitle}>Вхід</h2>
        {isError && error && (
          <p className={`${styles.errorText} ${styles.serverError}`}>{error}</p>
        )}
        <div className={styles.inputs}>
          <input
            type="email"
            placeholder="Email"
            className={styles.formInput}
            name="email"
            value={data.email}
            onChange={handleChange}
            required
          />

          <input
            type="password"
            placeholder="Пароль"
            className={styles.formInput}
            name="password"
            value={data.password}
            onChange={handleChange}
            required
          />

          <button
            type="submit"
            className={styles.submitButton}
            disabled={isPending}
          >
            Увійти
          </button>
        </div>
        <div className={styles.authSwitch}>
          <p>Не маєте акаунта?</p>
          <Link to="/register">Реєстрація</Link>
        </div>
      </form>
    </div>
  );
};

export default Login;
