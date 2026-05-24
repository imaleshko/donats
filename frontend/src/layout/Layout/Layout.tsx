import styles from "./Layout.module.css";
import { Outlet, useLocation } from "react-router";
import { Header } from "@/layout/Header/Header";
import { Footer } from "@/layout/Footer/Footer";
import { useEffect } from "react";

export const Layout = () => {
  const { pathname } = useLocation();

  useEffect(() => {
    window.scrollTo(0, 0);
  }, [pathname]);

  return (
    <div className={styles.wrapper}>
      <Header />
      <main className={styles.main}>
        <Outlet />
      </main>
      <Footer />
    </div>
  );
};
