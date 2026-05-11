import styles from "./Footer.module.css";
import { Logo } from "../Logo/Logo";

export const Footer = () => {
  return (
    <footer className={styles.footer}>
      <div className={styles.logo}>
        <Logo />
      </div>
    </footer>
  );
};
