import styles from "./Home.module.css";
import useFundraisers from "./useFundraisers.ts";
import { Card } from "@/pages/Home/Card/Card.tsx";
import { Link } from "react-router";
import { Feature } from "@/pages/Home/Feature/Feature.tsx";
import { useState } from "react";
import { Tags } from "@/pages/Home/Tags/Tags.tsx";

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
  const [activeTag, setActiveTag] = useState<string | null>(null);
  const { data, fetchNextPage, hasNextPage, isFetchingNextPage } =
    useFundraisers(activeTag);

  const fundraisers = data?.pages.flatMap((page) => page.fundraisers) ?? [];

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
            key={index}
          />
        ))}
      </section>

      <section className={styles.fundraisersList}>
        <p className={styles.newText}>Збори:</p>

        <Tags activeTag={activeTag} onChange={setActiveTag} />

        <div className={styles.cards}>
          {fundraisers.map((fundraiser) => (
            <Card key={fundraiser.id} {...fundraiser} />
          ))}
        </div>

        {hasNextPage && (
          <button
            className={styles.moreButton}
            onClick={() => fetchNextPage()}
            disabled={isFetchingNextPage}
          >
            {isFetchingNextPage ? "Завантаження" : "Ще"}
          </button>
        )}
      </section>
    </div>
  );
};
