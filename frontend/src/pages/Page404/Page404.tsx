import styles from "./Page404.module.css";

export const Page404 = () => {
  return (
    <div className={styles.wrapper}>
      <div className={styles.error}>404</div>
      <div className={styles.text}>Сторінку не знайдено</div>
    </div>
  );
};
