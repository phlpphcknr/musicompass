import styled from 'styled-components/macro'

export default function ReleaseDescription({release}){

    return (
        <Release>
            <img src={release.coverImageUrl} alt=""/>
            <section>
                <span>{release.fullTitle}</span>
                {release.releaseYear !== 0 && <span>{release.releaseYear}</span>}
            </section>
        </Release>
    )
}


const Release = styled.section`
  display: flex;
  align-items: center;
  padding: 10px;
  margin: 0px 20px;
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
    display: block;
    text-align: center;
    
    span{
      display: block;
      padding: 10px;
      font-size: 14px;
      color: var(--tertiary-color);
    }
    
  }
`
