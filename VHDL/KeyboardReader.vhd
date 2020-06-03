----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date:    00:08:07 06/03/2020 
-- Design Name: 
-- Module Name:    KeyboardReader - Structural 
-- Project Name: 
-- Target Devices: 
-- Tool versions: 
-- Description: 
--
-- Dependencies: 
--
-- Revision: 
-- Revision 0.01 - File Created
-- Additional Comments: 
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx primitives in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity KeyboardReader is
		PORT (
			RST: in STD_LOGIC;
			CLK : in  STD_LOGIC;
			ACK : in STD_LOGIC;
			DVal : out STD_LOGIC;
			Q : out STD_LOGIC_VECTOR (3 downto 0);
			PENC_IN: in  STD_LOGIC_VECTOR (3 downto 0);
			DEC_OUT : out STD_LOGIC_VECTOR (2 downto 0)
		);
end KeyboardReader;

architecture Structural of KeyboardReader is
	COMPONENT KeyDecoder
		Port( 
			RST: in STD_LOGIC;
			CLK : in  STD_LOGIC;
			KAck : in  STD_LOGIC;
			KVal: out STD_LOGIC;
			K : out  STD_LOGIC_VECTOR (3 downto 0);
			PENC_IN: in  STD_LOGIC_VECTOR (3 downto 0);
			DEC_OUT : out STD_LOGIC_VECTOR (2 downto 0)
	 );
	END COMPONENT;
	COMPONENT KeyBuffer
		Port( 
			CLK : in  STD_LOGIC;
			RST : in  STD_LOGIC;
			DAV : in  STD_LOGIC;
			ACK : in  STD_LOGIC;
			DAC : out  STD_LOGIC;
			DVal : out  STD_LOGIC;
			D : in STD_LOGIC_VECTOR (3 downto 0);
			Q : out STD_LOGIC_VECTOR (3 downto 0)
	 );
	END COMPONENT;
	SIGNAL VAL, INTERNAL_ACK : STD_LOGIC;
	SIGNAL INTERNAL_K : STD_LOGIC_VECTOR (3 downto 0);
begin
	KeyDecode : KeyDecoder
		PORT MAP ( 
			RST => RST,
			CLK => CLK,
			KAck => INTERNAL_ACK,
			KVal => VAL,
			K => INTERNAL_K,
			PENC_IN => PENC_IN,
			DEC_OUT => DEC_OUT
	 );
	
	KeyBuff : KeyBuffer
		PORT MAP ( 
			RST => RST,
			CLK => CLK,
			DAV => VAL,
			ACK => ACK,
			DAC => INTERNAL_ACK,
			DVal => DVal,
			D => INTERNAL_K,
			Q => Q
	 );
end Structural;

