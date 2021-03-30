import styled from 'styled-components/macro'
import {useParams} from "react-router-dom";

export default function ReleaseDescription({release}){

    const renderReleaseYear = () => {
        if (release.releaseYear === 0){
            return <></>
        } else {
            return <h3>{release.releaseYear}</h3>
        }
    }

    return (
        <Release>
            <img src={release.coverImageUrl} alt={'Shows the release cover'}/>
            <section>
                <h3>{release.fullTitle}</h3>
                {renderReleaseYear()}
            </section>
        </Release>
    )
}



const Release = styled.section`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  margin: 0px 20px 0px 20px;
  background-color: var(--primary-color);
  box-shadow: 0px 0px 6px #333;
  
  img {
    max-width: 120px;
    max-height: 120px;
    width: auto;
    height: auto;
    margin: 10px;
  }

  section {
    padding: 0px;
    margin: 0px;
    width: 100%;
    text-align: center;
    color: #636e72;
  }
  
`