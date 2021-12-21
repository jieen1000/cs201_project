import { makeStyles } from "@mui/styles";
import bca from "assets/images/bca-advisory.jpg";

/**
 * {@code AdvisoryCard} creates a component to display industry-related media releases.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

export default makeStyles(({ functions, palette, borders }) => {
  const { linearGradient, rgba, pxToRem } = functions;
  const { gradients } = palette;
  const { borderRadius } = borders;

  return {
    advisoryCard_content: {
      backgroundImage: `${linearGradient(
        rgba(gradients.dark.main, 0.8),
        rgba(gradients.dark.state, 0.8)
      )}, url(${bca})`,
      backgroundSize: "cover",
      display: "flex",
      flexDirection: "column",
      height: "100%",
      padding: pxToRem(16),
      borderRadius: borderRadius.lg,
    },

    advisoryCard_button: {
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

      "&:hover .material-icons-round, &:focus .material-icons-round": {
        transform: `translate(${pxToRem(6)}, ${pxToRem(-1)})`,
      },
    },
  };
});
