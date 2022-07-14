export const debounce = (fn, t) => {
    const delay = t || 500
    let timer
    return function () {
    const args = arguments
    if (timer) {
    clearTimeout(timer)
    }
    timer = setTimeout(() => {
    timer = null
    fn.apply(this, args)
    }, delay)
    }
}