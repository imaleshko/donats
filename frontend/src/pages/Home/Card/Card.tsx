import styles from "./Card.module.css";
import { Link } from "react-router";

interface CardProps {
  title: string;
  author: string;
  balance: number;
  goal: number;
  slug: string;
}

export const Card = ({ title, author, balance, goal, slug }: CardProps) => {
  const percentage = Math.min((balance / goal) * 100, 100);

  return (
    <div className={styles.card}>
      <div className={styles.content}>
        <div className={styles.info}>
          <p className={styles.title}>{title}</p>
          <p className={styles.author}>
            <span className={styles.for}>для</span>
            {author}
          </p>
        </div>
        <div className={styles.progress}>
          <div
            className={styles.progressFill}
            style={{ width: `${percentage}%` }}
          />
          <p className={styles.progressNumbers}>
            {goal > 0 ? `${balance}/${goal}` : `${balance}`}
          </p>
        </div>
      </div>

      <Link to={`/fundraiser/${author}/${slug}`} className={styles.button}>
        Донат
      </Link>
    </div>
  );
};
