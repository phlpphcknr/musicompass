import { createGlobalStyle } from 'styled-components'

export default createGlobalStyle`
  html, body, #root {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
    height: 100%;
    //font-size: 112.5%;
    font-family: "Courier New";
    background-color: var(--seondary-color);
    --primary-color: #fdcb6e;
    --secondary-color: moccasin;
    --opacity-hover:0.7;
  }
  h5{
    margin: 0;
    padding: 0;
  }
  
  a{
    text-decoration: none;
    color: chocolate;
    :hover {
      opacity: var(--opacity-hover);
    }
  }
  
  select{
    font-family: "Courier New";
  }
  
`
