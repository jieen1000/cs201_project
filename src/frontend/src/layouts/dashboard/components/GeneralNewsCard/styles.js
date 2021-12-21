import { makeStyles } from "@mui/styles";

/**
 * {@code GeneralNewsCard} creates a component to display general COVID-19 related media releases.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

export default makeStyles(({ functions }) => {
  const { pxToRem } = functions;

  return {
    generalNewsCard_button: {
      marginTop: "auto",
      marginRight: "auto",
      display: "inline-flex",
      alignItems: "center",
      cursor: "pointer",

      "& .material-icons-round": {
        fontSize: "1.125rem",
        transform: `translate(${pxToRem(2)}, ${pxToRem(-1)})`,
        transition: "transform 0.2s cubic-bezier(0.34,1.61,0.7,1.3)",
      },

      "&:hover .material-icons-round, &:focus  .material-icons-round": {
        transform: `translate(${pxToRem(6)}, ${pxToRem(-1)})`,
      },
    },

    ["@media only screen and (min-width: 768px)"]: { remove_img: {display: "none !important"} },
  };
});
