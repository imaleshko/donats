import styles from "./Logo.module.css";
import logo from "@/assets/logo.png";
import { Link } from "react-router";

export const Logo = () => {
  return (
    <Link to="/" className={styles.wrapper}>
      <p className={styles.text}>Donats</p>
      <img className={styles.image} src={logo} alt={"logo"} />
    </Link>
  );
};
