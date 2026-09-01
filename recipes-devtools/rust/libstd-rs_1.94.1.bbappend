# 针对 2k0300，在编译标准库时强制禁用 LSX 和 LASX
export RUSTFLAGS:append = " -C target-feature=-lsx,-lasx"
