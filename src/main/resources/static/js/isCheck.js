function isEmail(asValue) {

    // 이메일 형식에 맞게 입력했는지 체크
    let regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;


    return regExp.test(asValue); // 형식에 맞는 경우에만 true 리턴

}