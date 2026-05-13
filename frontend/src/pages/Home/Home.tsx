import styles from "./Home.module.css";
import useNewest from "./useNewest.ts";
import { Card } from "@/pages/Home/Card/Card.tsx";
import { Link } from "react-router";
import { Feature } from "@/pages/Home/Feature/Feature.tsx";

const FEATURES = [
  {
    title: "Швидкий запуск збору",
    description: "Розпочинайте новий збір за кілька секунд",
  },
  {
    title: "Гнучка комунікація",
    description:
      "Редагуйте збори, додавайте оновлення та переглядайте донати користувачів",
  },
  {
    title: "Зручні донати",
    description:
      "Здійснюйте анонімні донати з можливістю додати повідомлення за допомогою зручної системи",
  },
];

export const Home = () => {
  const { data: newest = [] } = useNewest();

  return (
    <div className={styles.wrapper}>
      <section className={styles.hero}>
        <h1 className={styles.heroTitle}>
          Створюйте новий збір та <br />
          починайте збирати кошти для{" "}
          <span className={styles.highlight}>своєї мети</span>
        </h1>
        <Link to="/account/fundraisers/create" className={styles.heroButton}>
          Новий збір
        </Link>
      </section>

      <section className={styles.features}>
        {FEATURES.map((feature, index) => (
          <Feature
            title={feature.title}
            description={feature.description}
            index={index}
          />
        ))}
      </section>

      <section className={styles.fundraisersList}>
        <p className={styles.newText}>Нові збори:</p>

        <div className={styles.cards}>
          {newest.map((fundraiser) => (
            <Card key={fundraiser.id} {...fundraiser} />
          ))}
        </div>
      </section>
    </div>
  );
};
