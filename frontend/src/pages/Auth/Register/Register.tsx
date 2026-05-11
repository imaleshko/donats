import styles from "@/pages/Auth/Auth.module.css";
import { type ChangeEvent, type SubmitEvent, useState } from "react";
import { Link } from "react-router";
import { useRegister } from "./useRegister.ts";

const Register = () => {
  const [data, setData] = useState({
    username: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const [errors, setErrors] = useState<Record<string, string>>({});

  const { register, isPending, isError, error } = useRegister();

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    if (errors[name]) {
      setErrors((prevErrors) => {
        const newErrors = { ...prevErrors };
        delete newErrors[name];
        return newErrors;
      });
    }

    setData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const validate = () => {
    const validationErrors: Record<string, string> = {};

    if (data.username.trim().length < 3) {
      validationErrors.username = "Ім’я має містити щонайменше 3 символи";
    }
    if (data.password.trim().length < 8) {
      validationErrors.password = "Пароль має містити щонайменше 8 символів";
    }
    if (data.password !== data.confirmPassword.trim()) {
      validationErrors.confirmPassword = "Паролі не співпадають";
    }

    return validationErrors;
  };

  const handleSubmit = (e: SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();

    const validationErrors = validate();
    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      return;
    }

    register({
      username: data.username.trim(),
      email: data.email.trim(),
      password: data.password,
    });
  };

  return (
    <div className={styles.wrapper}>
      <form className={styles.form} onSubmit={handleSubmit}>
        <h2 className={styles.formTitle}>Реєстрація</h2>
        {isError && error && (
          <p className={`${styles.errorText} ${styles.serverError}`}>{error}</p>
        )}

        <div className={styles.inputs}>
          <input
            type="text"
            placeholder="Ім'я користувача"
            className={styles.formInput}
            name="username"
            value={data.username}
            onChange={handleChange}
            required
          />
          {errors.username && (
            <p className={styles.errorText}>{errors.username}</p>
          )}

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
          {errors.password && (
            <p className={styles.errorText}>{errors.password}</p>
          )}

          <input
            type="password"
            placeholder="Повторіть пароль"
            className={styles.formInput}
            name="confirmPassword"
            value={data.confirmPassword}
            onChange={handleChange}
            required
          />
          {errors.confirmPassword && (
            <p className={styles.errorText}>{errors.confirmPassword}</p>
          )}

          <button
            type="submit"
            className={styles.submitButton}
            disabled={isPending}
          >
            Зареєструватися
          </button>
        </div>
        <div className={styles.authSwitch}>
          <p>Вже маєте акаунт?</p>
          <Link to="/login">Авторизація</Link>
        </div>
      </form>
    </div>
  );
};

export default Register;
