import styles from "./AccountLayout.module.css";
import { NavLink, Outlet } from "react-router";

const NAV_LINKS = [
  { to: "profile", label: "Мої дані" },
  { to: "donations", label: "Мої донати" },
  { to: "fundraisers", label: "Мої збори" },
];

const AccountLayout = () => {
  return (
    <div className={styles.wrapper}>
      <nav className={styles.sidebar}>
        {NAV_LINKS.map(({ to, label }) => (
          <NavLink
            key={to}
            to={to}
            className={({ isActive }) =>
              isActive ? `${styles.navLink} ${styles.active}` : styles.navLink
            }
          >
            {label}
          </NavLink>
        ))}
      </nav>

      <main className={styles.content}>
        <Outlet />
      </main>
    </div>
  );
};

export default AccountLayout;
