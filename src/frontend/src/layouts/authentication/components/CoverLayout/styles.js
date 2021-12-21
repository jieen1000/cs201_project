import { makeStyles } from "@mui/styles";

/**
 * {@code BasicLayout} provides the basic layout for authentication page.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

export default makeStyles(({ functions, borders }) => {
  const { pxToRem } = functions;
  const { borderRadius } = borders;

  return {
    coverLayout: {
      minHeight: "75vh",
      margin: 0,
    },

    coverLayout_imageBox: {
      transform: "skewX(-10deg)",
      height: "100%",
      overflow: "hidden",
      marginRight: pxToRem(-128),
      borderBottomLeftRadius: borderRadius.lg,
    },

    coverLayout_image: {
      backgroundImage: ({ image }) => `url(${image})`,
      backgroundSize: "cover",
      transform: "skewX(10deg)",
      marginLeft: pxToRem(-64),
      height: "100%",
    },
  };
});
