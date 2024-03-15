function isEmail(asValue) {

    // 이메일 형식에 맞게 입력했는지 체크
    let regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
    return regExp.test(asValue); // 형식에 맞는 경우에만 true 리턴

}

async function exEmail(email) { // 이메일 비동기 존재 확인
    console.log(email);
    const response = await axios.post(`/member/exEmail/${email}`);
    return response.data;
}

async function sendConfirmMail(mailTo) { // 이메일 인증문자 발송
    // console.log("sendConfirmMail");
    // console.log(mailTo);
    const request = await axios.get(`/member/sendConfirmMail/${mailTo}`);
    return request.data;
}

async function matchConfirmKey(confirmKey) { // 이메일 인증문자 확인
    // console.log(confirmKey);
    const request = await axios.post(`/member/matchConfirmKey/${confirmKey}`);
    // console.log("axios: " + request.data);
    return request.data;
}
