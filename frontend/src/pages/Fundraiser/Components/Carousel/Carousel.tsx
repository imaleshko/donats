import styles from "./Carousel.module.css";
import useEmblaCarousel from "embla-carousel-react";

interface CarouselProps {
  imageUrls: string[];
}

const Carousel = ({ imageUrls }: CarouselProps) => {
  const [emblaRef, emblaApi] = useEmblaCarousel({ loop: true });

  const scrollPrev = () => emblaApi?.scrollPrev();
  const scrollNext = () => emblaApi?.scrollNext();

  if (imageUrls.length === 0) {
    return <div>Зображення відсутні</div>;
  }

  return (
    <div className={styles.carouselWrapper}>
      <div className={styles.embla} ref={emblaRef}>
        <div className={styles.emblaContainer}>
          {imageUrls.map((url) => (
            <div className={styles.emblaSlide} key={url}>
              <img src={url} alt="Зображення збору" className={styles.image} />
            </div>
          ))}
        </div>
      </div>

      {imageUrls.length > 1 && (
        <>
          <button
            type="button"
            className={`${styles.arrow} ${styles.arrowLeft}`}
            onClick={scrollPrev}
          >
            ❮
          </button>

          <button
            type="button"
            className={`${styles.arrow} ${styles.arrowRight}`}
            onClick={scrollNext}
          >
            ❯
          </button>
        </>
      )}
    </div>
  );
};

export default Carousel;
