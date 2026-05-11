import styles from "./Update.module.css";
import MDEditor from "@uiw/react-md-editor";

interface UpdateProps {
  title: string;
  message: string;
  createdAt: string;
}

const Update = ({ title, message, createdAt }: UpdateProps) => {
  const date = new Date(createdAt).toLocaleDateString("uk-UA");

  return (
    <div className={styles.wrapper}>
      <div className={styles.header}>
        <h2 className={styles.title}>{title}</h2>
        <span className={styles.date}>{date}</span>
      </div>

      <MDEditor.Markdown
        source={message}
        style={{
          backgroundColor: "transparent",
          color: "var(--text-grey-color)",
        }}
      />
    </div>
  );
};

export default Update;
