import { Logo } from "../Logo/Logo";
import styles from "./Header.module.css";
import { Link } from "react-router";
import useLogout from "./useLogout.ts";
import defaultAvatar from "@/assets/defaultAvatar.png";
import useGetUser from "@/app/useGetUser.ts";

export const Header = () => {
  const { data: user } = useGetUser();
  const { mutate: logout, isPending } = useLogout();

  return (
    <header className={styles.header}>
      <div>
        <Logo />
      </div>

      <div className={styles.actions}>
        {user ? (
          <div className={styles.userMenu}>
            <div className={styles.avatarWrapper}>
              <img
                src={user.avatarUrl || defaultAvatar}
                alt="Профіль"
                className={styles.avatar}
              />
            </div>

            <div className={styles.dropdown}>
              <Link to="/account/profile" className={styles.dropdownItem}>
                Мої дані
              </Link>
              <Link to="/account/donations" className={styles.dropdownItem}>
                Мої донати
              </Link>
              <Link to="/account/fundraisers" className={styles.dropdownItem}>
                Мої збори
              </Link>

              <div className={styles.dropdownDivider}></div>

              <button
                className={styles.dropdownLogout}
                onClick={() => logout()}
                disabled={isPending}
              >
                Вихід
              </button>
            </div>
          </div>
        ) : (
          <div className={styles.buttons}>
            <Link to="/login" className={styles.loginButton}>
              Увійти
            </Link>
            <Link to="/register" className={styles.regButton}>
              Створити акаунт
            </Link>
          </div>
        )}
      </div>
    </header>
  );
};
